package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.collections.EmployerDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.collections.UserDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.collections.VacancyDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.collections.VacancyRequirementDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.mybatis.EmployerDaoImpl;
import net.thumbtack.school.hiring.daoimpl.mybatis.UserDaoImpl;
import net.thumbtack.school.hiring.daoimpl.mybatis.VacancyDaoImpl;
import net.thumbtack.school.hiring.daoimpl.mybatis.VacancyRequirementDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.mapper.EmployerMapper;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.model.VacancyRequirement;
import net.thumbtack.school.hiring.utils.DaoType;
import net.thumbtack.school.hiring.utils.HiringUtils;
import net.thumbtack.school.hiring.utils.ServerUtils;
import net.thumbtack.school.hiring.utils.Settings;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployerService extends UserService{
    private static final Gson gson = new Gson();
    private final Settings settings = Settings.getInstance();

    private final EmployerDaoImpl employerDao = new EmployerDaoImpl();
    private final VacancyDaoImpl vacancyDao = new VacancyDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final VacancyRequirementDaoImpl vacancyRequirementDao = new VacancyRequirementDaoImpl();

    private final EmployerDaoImplCol employerDaoImplCol = new EmployerDaoImplCol();
    private final VacancyDaoImplCol vacancyDaoImplCol = new VacancyDaoImplCol();
    private final UserDaoImplCol userDaoImplCol = new UserDaoImplCol();
    private final VacancyRequirementDaoImplCol vacancyRequirementDaoImplCol = new VacancyRequirementDaoImplCol();

    private final int CODE = 200;


    public Response registerEmployer(String json) throws JsonSyntaxException {
        try {
            RegisterEmployerDtoRequest employerDtoRequest = ServerUtils.getClassFromJson(json, RegisterEmployerDtoRequest.class);
            Employer employer = EmployerMapper.INSTANCE.registerEmployerDtoToEmployer(employerDtoRequest);
            validateEmployer(employer);
            int id;
            if (settings.getDaoType().equals(DaoType.SQL)) {
                id = employerDao.insert(employer);
            } else {
                id = employerDaoImplCol.insert(employer);
            }
            return Response.ok(new RegisterDtoResponse(id), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response updateEmployer(String json, String token) {
        try {
            UpdateEmployerDtoRequest employerDtoRequest = ServerUtils.getClassFromJson(json, UpdateEmployerDtoRequest.class);
            Employer employer = new Employer(employerDtoRequest.getTitle(), employerDtoRequest.getAddress(),
                    employerDtoRequest.getEmail(),
                    employerDtoRequest.getSurname(), employerDtoRequest.getName(), employerDtoRequest.getPatronymic(),
                    employerDtoRequest.getLogin(), employerDtoRequest.getPassword());
            validateEmployer(employer);

            if (settings.getDaoType().equals(DaoType.SQL)) {
                employerDao.update(employer, employerDtoRequest.getToken());
                userDao.update(employer, employerDtoRequest.getToken());
            } else {
                employerDaoImplCol.update(employer, employerDtoRequest.getToken());
                userDaoImplCol.update(employer, employerDtoRequest.getToken());
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response removeEmployer(String token) {
        try {
            Employer employer = getEmployerByToken(token);
            if (settings.getDaoType().equals(DaoType.SQL)) {
                userDao.delete(employer.getId());
            } else {
                userDaoImplCol.delete(employer.getId());
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Employer getEmployerById(int id) {
        if (settings.getDaoType().equals(DaoType.SQL)) {
            return employerDao.getEmployerById(id);
        } else {
            return employerDaoImplCol.getEmployerById(id);
        }
    }

    public Response addVacancy(String token, String json){
        try{
            Employer employer = getEmployerByToken(token);
            AppendVacancyDtoRequest appendVacancyDtoRequest = ServerUtils.getClassFromJson(json, AppendVacancyDtoRequest.class);
            Vacancy vacancy = new Vacancy(appendVacancyDtoRequest.getJobTitle(), appendVacancyDtoRequest.getSalary());
            validateVacancy(vacancy);

            employer.addVacancy(vacancy);

            int id;
            if (settings.getDaoType().equals(DaoType.SQL)) {
                id = vacancyDao.insert(vacancy, employer);
            } else {
                id = vacancyDaoImplCol.insert(vacancy, employer);
            }
            return Response.ok(new AppendVacancyDtoResponse(id), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex){
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response removeVacancy(String token, int vacancyId){
        try{
            Employer employer = getEmployerByToken(token);

            Vacancy vacancy = getVacancyById(vacancyId);
            vacancy.clearVacancyRequirement();
            employer.deleteVacancy(vacancy);

            if (settings.getDaoType().equals(DaoType.SQL)) {
                vacancyDao.delete(token, vacancyId);
            } else {
                vacancyDaoImplCol.delete(token, vacancyId);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex){
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response updateVacancy(String token, String json, int vacancyId) {
        try {
            UpdateVacancyDtoRequest request = ServerUtils.getClassFromJson(json, UpdateVacancyDtoRequest.class);
            Vacancy vacancy = new Vacancy(vacancyId, request.getJobTitle(), request.getSalary(), request.isStatus());
            if (settings.getDaoType().equals(DaoType.SQL)) {
                vacancyDao.update(token, vacancy);
            } else {
                vacancyDaoImplCol.update(token, vacancy);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }


    public Response getVacanciesByEmployer(String json) {
        try {
            EmployerIdDtoRequest request = ServerUtils.getClassFromJson(json, EmployerIdDtoRequest.class);
            Set<VacancyDtoResponse> result = new HashSet<>();
            Set<Vacancy> vacancies;
            int id = request.getId();
            Employer employer = getEmployerById(id);
            if (settings.getDaoType().equals(DaoType.SQL)) {
                vacancies = vacancyDao.getVacanciesByEmployer(employer);
                for (Vacancy vacancy : vacancies) {
                    result.add(new VacancyDtoResponse(vacancy.getId(), vacancy.getJobTitle(), vacancy.getSalary(),
                            vacancy.getVacancyRequirements(), vacancy.isStatus()));
                }
            } else {
                vacancies = vacancyDaoImplCol.getVacanciesByEmployer(employer);
                for (Vacancy vacancy : vacancies) {
                    result.add(new VacancyDtoResponse(vacancy.getId(), vacancy.getJobTitle(), vacancy.getSalary(),
                            vacancy.getVacancyRequirements(), vacancy.isStatus()));
                }
            }
            return Response.ok(new SetVacanciesByEmployerDtoResponse(result), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }


    public Response addVacancyRequirement(String token, String json) {
        try {
            Employer employer = getEmployerByToken(token);
            AppendVacancyRequirementDtoRequest requirementDtoRequest = ServerUtils.getClassFromJson(json, AppendVacancyRequirementDtoRequest.class);
            VacancyRequirement vacancyRequirement = new VacancyRequirement(requirementDtoRequest.getDescription(),
                    requirementDtoRequest.getLevel(), requirementDtoRequest.isObligatory());
            Vacancy vacancy = getVacancyById(requirementDtoRequest.getVacancyId());

            int id;
            if (settings.getDaoType().equals(DaoType.SQL)) {
                id = vacancyRequirementDao.insert(vacancyRequirement, vacancy);
            }
            else {
                id = vacancyRequirementDaoImplCol.insert(vacancyRequirement, vacancy);
            }

            AppendVacancyRequirementDtoResponse addRequirementDtoResponse = new AppendVacancyRequirementDtoResponse(id);
            return Response.ok(addRequirementDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response updateVacancyRequirement(String json, int vacancyRequirementId) {
        try {
            getVacancyRequirementById(vacancyRequirementId);
            UpdateVacancyRequirementDtoRequest request = ServerUtils.getClassFromJson(json,
                    UpdateVacancyRequirementDtoRequest.class);
            VacancyRequirement vacancyRequirement = new VacancyRequirement(request.getId(), request.getDescription(),
                    request.getLevel(), request.isObligatory());
            validateVacancyRequirement(vacancyRequirement);

            if (settings.getDaoType().equals(DaoType.SQL)) {
                vacancyRequirementDao.update(request.getVacancyId(), vacancyRequirement);
            } else {
                vacancyRequirementDaoImplCol.update(request.getVacancyId(), vacancyRequirement);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(new ErrorDtoResponse(ex.getErrorCode(), ex.getMessage())))
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    public Response removeVacancyRequirement(String token,int vacancyId, int vacancyRequirementId) {
        try {
            getEmployerByToken(token);
            VacancyRequirement vacancyRequirement = getVacancyRequirementById(vacancyRequirementId);

            if (settings.getDaoType().equals(DaoType.SQL)) {
                vacancyRequirementDao.delete(vacancyId, vacancyRequirementId);
            } else {
                vacancyRequirementDaoImplCol.delete(vacancyId, vacancyRequirementId);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(new ErrorDtoResponse(ex.getErrorCode(), ex.getMessage())))
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    public Response listEmployeesSuitableVacancyLevelNotLower(String json) {
        try {
            VacancyIdDtoRequest request = ServerUtils.getClassFromJson(json, VacancyIdDtoRequest.class);
            List<List<Employee>> result = new ArrayList<>();
            Vacancy vacancy;
            if (settings.getDaoType().equals(DaoType.SQL)) {
                vacancy = vacancyDao.getVacancyById(request.getId());
                Set<VacancyRequirement> vacancyRequirementList = vacancy.getVacancyRequirements();
                for (VacancyRequirement vr : vacancyRequirementList) {
                    List<Employee> help = employerDao.getEmployeesByVacancyRequirement(vr);
                    result.add(help);
                }
            } else {
                vacancy = vacancyDaoImplCol.getVacancyById(request.getId());
                Set<VacancyRequirement> vacancyRequirementList = vacancyRequirementDaoImplCol.getVacancyRequirementsByVacancy(vacancy);
                for (VacancyRequirement vr : vacancyRequirementList) {
                    List<Employee> help = employerDaoImplCol.getEmployeesByVacancyRequirement(vr);
                    result.add(help);
                }
            }

            List<Employee> first = result.get(0);
            for(List<Employee> elem : result) {
                first.retainAll(elem);
            }

            List<EmployeeDtoResponse> result1 = new ArrayList<>();
            for(Employee employee : first) {
                result1.add(new EmployeeDtoResponse(employee.getId(),employee.getEmail(), employee.getSurname(), employee.getName(),
                        employee.getPatronymic(), employee.getLogin(), employee.getPassword()));
            }

            ListOfEmployeesDtoResponse listOfEmployeesDtoResponse = new ListOfEmployeesDtoResponse(result1);
            return Response.ok(listOfEmployeesDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(new ErrorDtoResponse(ex.getErrorCode(), ex.getMessage())))
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    private void validateEmployer(Employer request) throws ServerException{
        if (request.getName() == null || request.getName().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_NAME);
        }
        if (request.getSurname() == null || request.getSurname().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_SURNAME);
        }
        if (request.getPatronymic() == null){
            throw new ServerException(ErrorCode.WRONG_PATRONYMIC);
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_EMAIL);
        }
        if (request.getLogin() == null || request.getLogin().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
        if (request.getTitle() == null || request.getTitle().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_TITLE);
        }
        if (request.getAddress() == null || request.getAddress().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_ADDRESS);
        }
    }

    public void validateVacancy(Vacancy vacancy) throws ServerException{
        if(vacancy.getJobTitle() == null || vacancy.getJobTitle().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_JOB_TITLE);
        }
        if(vacancy.getSalary() < 0){
            throw new ServerException(ErrorCode.WRONG_SALARY);
        }
    }

    public void validateVacancyRequirement(VacancyRequirement vacancyRequirement) throws ServerException {
        if(vacancyRequirement.getLevel() < 1 || vacancyRequirement.getLevel() > 5) {
            throw new ServerException(ErrorCode.WRONG_SKILL_LEVEL);
        }
        if(vacancyRequirement.getDescription() == null || vacancyRequirement.getDescription().isEmpty()) {
            throw new ServerException(ErrorCode.WRONG_SKILL_DESCRIPTION);
        }
    }



    public Employer getEmployerByToken(String token) throws ServerException{
        if(token == null){
            throw new ServerException(ErrorCode.WRONG_TOKEN);
        }
        Employer employer;
        if (settings.getDaoType().equals(DaoType.SQL)) {
            employer = employerDao.getEmployerByToken(token);
        } else {
            employer = employerDaoImplCol.getEmployerByToken(token);
        }
        if (employer == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }
        return employer;
    }

    public Vacancy getVacancyById(int id) throws ServerException {
        Vacancy vacancy;
        if (settings.getDaoType().equals(DaoType.SQL)) {
            vacancy = vacancyDao.getVacancyById(id);
        }
        else {
            vacancy = vacancyDaoImplCol.getVacancyById(id);
        }

        if (vacancy == null) {
            throw new ServerException(ErrorCode.INVALID_ID);
        }
        return vacancy;
    }

    private VacancyRequirement getVacancyRequirementById(int id) throws ServerException {
        VacancyRequirement vacancyRequirement;
        if (settings.getDaoType().equals(DaoType.SQL)) {
            vacancyRequirement = vacancyRequirementDao.getVacancyRequirementById(id);
        }
        else {
            vacancyRequirement = vacancyRequirementDaoImplCol.getVacancyRequirementById(id);
        }

        if (vacancyRequirement == null) {
            throw new ServerException(ErrorCode.INVALID_ID);
        }
        return vacancyRequirement;
    }

}
