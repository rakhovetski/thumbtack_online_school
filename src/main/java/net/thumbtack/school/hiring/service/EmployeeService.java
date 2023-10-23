package net.thumbtack.school.hiring.service;

import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.collections.EmployeeDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.collections.SkillDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.collections.UserDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.mybatis.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.mybatis.SkillDaoImpl;
import net.thumbtack.school.hiring.daoimpl.mybatis.UserDaoImpl;
import net.thumbtack.school.hiring.dto.request.AppendSkillDtoRequest;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.UpdateEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.UpdateSkillDtoRequest;
import net.thumbtack.school.hiring.dto.response.AppendSkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.RegisterDtoResponse;
import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.mapper.EmployeeMapper;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.utils.DaoType;
import net.thumbtack.school.hiring.utils.HiringUtils;
import net.thumbtack.school.hiring.utils.ServerUtils;
import net.thumbtack.school.hiring.utils.Settings;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EmployeeService extends UserService{
    private static final Settings settings = Settings.getInstance();

    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final SkillDaoImpl skillDao = new SkillDaoImpl();

    private final EmployeeDaoImplCol employeeDaoImplCol = new EmployeeDaoImplCol();
    private final UserDaoImplCol userDaoImplCol = new UserDaoImplCol();
    private final SkillDaoImplCol skillDaoImplCol = new SkillDaoImplCol();


    public Response registerEmployee(String json) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest employeeDtoRequest = ServerUtils.getClassFromJson(json, RegisterEmployeeDtoRequest.class);
            Employee employee = EmployeeMapper.INSTANCE.registerEmployeeDtoToEmployee(employeeDtoRequest);
            validateEmployee(employee);
            int id;
            if (settings.getDaoType().equals(DaoType.SQL)) {
                id = employeeDao.insert(employee);
            } else {
                id = employeeDaoImplCol.insert(employee);
            }
            return Response.ok(new RegisterDtoResponse(id), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response updateEmployee(String json, String token) {
        try {
            UpdateEmployeeDtoRequest employeeDtoRequest = ServerUtils.getClassFromJson(json, UpdateEmployeeDtoRequest.class);
            Employee employee = new Employee(employeeDtoRequest.getEmail(),
                    employeeDtoRequest.getSurname(), employeeDtoRequest.getName(), employeeDtoRequest.getPatronymic(),
                    employeeDtoRequest.getLogin(), employeeDtoRequest.getPassword(), employeeDtoRequest.isStatus());
            validateEmployee(employee);

            if (settings.getDaoType().equals(DaoType.SQL)) {
                employeeDao.update(employee, employeeDtoRequest.getToken());
                userDao.update(employee, employeeDtoRequest.getToken());
            } else {
                employeeDaoImplCol.update(employee, employeeDtoRequest.getToken());
                userDaoImplCol.update(employee, employeeDtoRequest.getToken());
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response removeEmployee(String token) {
        try {
            Employee employee = getEmployeeByToken(token);
            if (settings.getDaoType().equals(DaoType.SQL)) {
                userDao.delete(employee.getId());
            } else {
                userDaoImplCol.delete(employee.getId());
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response addSkill(String token, String json){
        try{
            Employee employee = getEmployeeByToken(token);
            AppendSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(json, AppendSkillDtoRequest.class);
            Skill skill = new Skill(skillDtoRequest.getDescription(), skillDtoRequest.getLevel());
            validateSkill(skill);

            int id;
            if (settings.getDaoType().equals(DaoType.SQL)) {
                id = skillDao.insert(skill, employee);
            } else {
                id = skillDaoImplCol.insert(skill, employee);
            }
            return Response.ok(new AppendSkillDtoResponse(id), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex){
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response removeSkill(String token, int skillId){
        try{
            Employee employee = getEmployeeByToken(token);
            if (settings.getDaoType().equals(DaoType.SQL)) {
                skillDao.delete(token, skillId);
            } else {
                skillDaoImplCol.delete(token, skillId);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex)  {
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response updateSkill(String token, String json, int skillId) {
        try {
            Employee employee = getEmployeeByToken(token);
            Skill skill1 = getSkillById(skillId);
            UpdateSkillDtoRequest request = ServerUtils.getClassFromJson(json, UpdateSkillDtoRequest.class);
            Skill skill = new Skill(request.getId(), request.getDescription(), request.getLevel());
            if (settings.getDaoType().equals(DaoType.SQL)) {
                skillDao.update(request.getEmployeeToken(), skill);
            } else {
                skillDaoImplCol.update(request.getEmployeeToken(), skill);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return HiringUtils.failureResponse(ex);
        }
    }


    public Employee getEmployeeByToken(String token) throws ServerException{
        if(token == null){
            throw new ServerException(ErrorCode.WRONG_TOKEN);
        }
        Employee employee;
        if (settings.getDaoType().equals(DaoType.SQL)) {
            employee = employeeDao.getEmployeeByToken(token);
        } else {
            employee = employeeDaoImplCol.getEmployeeByToken(token);
        }
        if (employee == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }
        return employee;
    }

    public Skill getSkillById(int id) {
        if (settings.getDaoType().equals(DaoType.SQL)) {
            return skillDao.getSkillById(id);
        } else {
            return skillDaoImplCol.getSkillById(id);
        }
    }


    private void validateEmployee(Employee request) throws ServerException{
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
    }

    public void validateSkill(Skill skill) throws ServerException{
        if(skill.getDescription() == null || skill.getDescription().isEmpty()){
            throw new ServerException(ErrorCode.WRONG_SKILL_DESCRIPTION);
        }
        if(skill.getLevel() < 1 || skill.getLevel() > 5){
            throw new ServerException(ErrorCode.WRONG_SKILL_LEVEL);
        }
    }

    public Employee getEmployeeById(int id) {
        if (settings.getDaoType().equals(DaoType.SQL)) {
            return employeeDao.getEmployeeById(id);
        } else {
            return employeeDaoImplCol.getEmployeeById(id);
        }
    }
}
