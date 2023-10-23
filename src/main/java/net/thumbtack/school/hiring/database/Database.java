package net.thumbtack.school.hiring.database;

import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private static Database instance = null;

    private AtomicInteger idUser = new AtomicInteger(1);
    private AtomicInteger idSkill = new AtomicInteger(1);
    private AtomicInteger idVacancy = new AtomicInteger(1);
    private AtomicInteger idVacancyRequirement = new AtomicInteger(1);

    private final Map<String, User> userByToken = new ConcurrentHashMap<>();
    private final Map<String, User> userByLogin = new ConcurrentHashMap<>();
    private final Map<Integer, User> userById = new ConcurrentHashMap<>();
    private final Map<Integer, Vacancy> vacancyById = new ConcurrentHashMap<>();
    private final Map<Integer, Skill> skillById = new ConcurrentHashMap<>();
    private final Map<Integer, VacancyRequirement> vacancyRequirementById = new ConcurrentHashMap<>();

    private final MultiMapSynch<Integer, Skill> skillsByEmployeeId = new MultiMapSynch<>();
    private final MultiMapSynch<Integer, Vacancy> vacanciesByEmployerId = new MultiMapSynch<>();
    private final MultiMapSynch<Integer, VacancyRequirement> vacancyRequirementsByVacancyId = new MultiMapSynch<>();


    public synchronized static Database getInstance(){
        if (instance == null) {
            return instance = new Database();
        }
        return instance;
    }

    public int register(User user) throws ServerException {
        user.setId(idUser.get());
        if(userByLogin.putIfAbsent(user.getLogin(), user) != null){
            throw new ServerException(ErrorCode.LOGIN_IS_USED);
        }
        int id = idUser.getAndIncrement();
        userById.put(id, user);
        userByLogin.put(user.getLogin(), user);
        return id;
    }


    public void replaceUser(User userNew, String token) throws ServerException {
        User userOld = getUserByToken(token);
        if (userOld == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        userById.replace(userOld.getId(), userNew);
        userByLogin.replace(userOld.getLogin(), userNew);
        userByToken.replace(token, userNew);
    }


    public void removeUser(int id) throws ServerException{
        User user = getUserById(id);
        if (user == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        userById.remove(id);
        userByLogin.remove(user.getLogin());
    }


    public void deleteAllUsers() {
        userByToken.clear();
        userById.clear();
        userByLogin.clear();
        skillsByEmployeeId.clear();
        vacanciesByEmployerId.clear();
    }


    public List<User> getAllUsers() {
        return new ArrayList<>(userById.values());
    }


    public String login(User user) throws ServerException {
        if(user != null){
            User user2 = userByLogin.get(user.getLogin());
            if (user.getPassword().equals(user2.getPassword())) {
                userByToken.put(user2.getToken(), user);
                return user2.getToken();
            } else {
                throw new ServerException(ErrorCode.WRONG_PASSWORD);
            }
        } else {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public void logout(String token) throws ServerException{
        if (userByToken.remove(token) == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }
    }


    public void replaceEmployer(Employer employer, String token) throws ServerException{
        Employer employerOld = (Employer) getUserByToken(token);
        if (employerOld == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        userById.replace(employerOld.getId(), employer);
        userByLogin.replace(employerOld.getLogin(), employer);
        userByToken.replace(token, employer);
    }


    public List<Employer> getAllEmployers() {
        List<Employer> result = new ArrayList<>();
        for(User user : userById.values()) {
            if (user instanceof Employer) {
                result.add((Employer) user);
            }
        }
        return result;
    }


    public List<Employee> getEmployeesByVacancyRequirement(VacancyRequirement vacancyRequirement) {
        List<Employee> result = new ArrayList<>();
        for(int id : skillsByEmployeeId.keySet()) {
            Set<Skill> skills = skillsByEmployeeId.get(id);
            for( Skill skill : skills) {
                if(skill.getDescription().equals(vacancyRequirement.getDescription())) {
                    if(skill.getLevel() >= vacancyRequirement.getLevel()) {
                        result.add((Employee) getUserById(id));
                    }
                }
            }
        }
        return result;
    }


    public void replaceEmployee(Employee employee, String token) throws ServerException{
        Employee employeeOld = (Employee) getUserByToken(token);
        if (employeeOld == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        userById.replace(employeeOld.getId(), employee);
        userByLogin.replace(employeeOld.getLogin(), employee);
        userByToken.replace(token, employee);
    }


    public List<Employee> getAllEmployees() {
        List<Employee> result = new ArrayList<>();
        for(User user : userById.values()) {
            if (user instanceof Employee) {
                result.add((Employee) user);
            }
        }
        return result;
    }


    public int addVacancy(Employer employer, Vacancy vacancy) {
        vacancy.setId(idVacancy.get());
        int id = idVacancy.getAndIncrement();

        vacancyById.put(id, vacancy);
        vacanciesByEmployerId.put(employer.getId(), vacancy);

        for(VacancyRequirement vr : vacancy.getVacancyRequirements()) {
            vacancyRequirementsByVacancyId.put(vacancy.getId(), vr);
        }

        return id;
    }


    public void replaceVacancy(Vacancy newVacancy, String token) {
        Employer employer = (Employer) userByToken.get(token);

        vacancyById.remove(newVacancy.getId());
        vacancyById.put(newVacancy.getId(), newVacancy);

        vacanciesByEmployerId.replace(employer.getId(), getVacancyById(newVacancy.getId()), newVacancy);
    }


    public void removeVacancy(String token, int vacancyId){
        Employer employer = (Employer) userByToken.get(token);
        Vacancy vacancy = getVacancyById(vacancyId);

        vacancyById.remove(vacancyId);
        vacanciesByEmployerId.removeMapping(employer.getId(), vacancy);
        vacancyRequirementsByVacancyId.remove(vacancyId);
    }


    public void deleteAllVacancies() {
        vacancyById.clear();
        vacanciesByEmployerId.clear();
    }


    public Set<Vacancy> getAllVacancies() {
        return new HashSet<>(vacancyById.values());
    }


    public Set<Vacancy> getVacanciesByEmployer(Employer employer) {
        Set<Vacancy> vacancies = vacanciesByEmployerId.get(employer.getId());
        Set<Vacancy> result = new HashSet<>();

        for(Vacancy vacancy : vacancies) {
            Set<VacancyRequirement> vrSet = getVacancyRequirementByVacancy(vacancy);
            for(VacancyRequirement vr : vrSet) {
                vacancy.addVacancyRequirement(vr);
            }
            result.add(vacancy);
        }
        return result;
    }


    public Integer getIdVacancyByName(String name) throws ServerException{
        for (Vacancy vacancy : vacancyById.values()) {
            if (vacancy.getJobTitle().equals(name)) {
                return vacancy.getId();
            }
        }
        throw new ServerException(ErrorCode.WRONG_VACANCY_NAME);
    }


    public int addVacancyRequirement(Vacancy vacancy, VacancyRequirement vacancyRequirement){
        vacancyRequirement.setId(idVacancyRequirement.get());
        int id = idVacancyRequirement.getAndIncrement();

        vacancyRequirementById.put(id, vacancyRequirement);
        vacancyRequirementsByVacancyId.put(vacancy.getId(), vacancyRequirement);

        return id;
    }


    public void replaceVacancyRequirement(int vacancyId, VacancyRequirement vacancyRequirement) {
        vacancyRequirementById.remove(vacancyRequirement.getId());
        vacancyRequirementById.put(vacancyRequirement.getId(), vacancyRequirement);

        vacancyRequirementsByVacancyId.replace(vacancyId, getVacancyRequirementById(vacancyRequirement.getId()),
                vacancyRequirement);
    }


    public void removeVacancyRequirement(int vacancyId, int vacancyRequirementId){
        vacancyRequirementById.remove(vacancyRequirementId);

        VacancyRequirement vacancyRequirement = getVacancyRequirementById(vacancyRequirementId);
        vacancyRequirementsByVacancyId.removeMapping(vacancyId, vacancyRequirement);
    }


    public void deleteAllVacancyRequirements() {
        vacancyRequirementById.clear();
        vacancyRequirementsByVacancyId.clear();
    }


    public Set<VacancyRequirement> getVacancyRequirementByVacancy(Vacancy vacancy) {
        return new HashSet<>(vacancyRequirementsByVacancyId.get(vacancy.getId()));
    }


    public Set<VacancyRequirement> getAllVacancyRequirements() {
        return new HashSet<>(vacancyRequirementById.values());
    }


    public int addSkill(Employee employee, Skill skill){
        skill.setId(idSkill.get());
        int id = idSkill.getAndIncrement();
        skillById.put(id, skill);
        skillsByEmployeeId.put(employee.getId(), skill);

        return id;
    }


    public void replaceSkill(Skill skill, String token) {
        Employee employee = (Employee) userByToken.get(token);

        skillById.remove(skill.getId());
        skillById.put(skill.getId(), skill);

        skillsByEmployeeId.replace(employee.getId(), getSkillById(skill.getId()), skill);
    }


    public void removeSkill(String token, int skillId){
        Employee employee = (Employee) userByToken.get(token);
        Skill skill = getSkillById(skillId);

        skillById.remove(skill.getId());
        skillsByEmployeeId.removeMapping(employee.getId(), skill);
    }


    public Set<Skill> getAllSkills() {
        return new HashSet<> (skillById.values());
    }


    public Set<Skill> getSkillsByEmployee(Employee employee) {
        return new HashSet<>(skillsByEmployeeId.get(employee.getId()));
    }


    public void deleteAllSkills() {
        skillById.clear();
        skillsByEmployeeId.clear();
    }



    public void clear() {
        userByToken.clear();
        userById.clear();
        userByLogin.clear();
        skillById.clear();
        vacancyById.clear();
        vacanciesByEmployerId.clear();
        vacancyRequirementsByVacancyId.clear();
        skillsByEmployeeId.clear();
    }



    public User getUserByToken(String token){
        return userByToken.get(token);
    }


    public User getUserByLogin(String login){
        return userByLogin.get(login);
    }


    public User getUserById(int id) {
        return userById.get(id);
    }


    public Vacancy getVacancyById(int id) {
        return vacancyById.get(id);
    }


    public VacancyRequirement getVacancyRequirementById(int id) {
        return (VacancyRequirement) skillById.get(id);
    }


    public Integer getIdByUser(String login) throws ServerException {
        for (User user : userById.values()) {
            if (user.getLogin().equals(login)) {
                return user.getId();
            }
        }
        throw new ServerException(ErrorCode.WRONG_LOGIN);
    }


    public Skill getSkillById(int id){
        return skillById.get(id);
    }
}
