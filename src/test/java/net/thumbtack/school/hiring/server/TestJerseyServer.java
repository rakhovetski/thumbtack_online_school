package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.client.ServerClient;
import net.thumbtack.school.hiring.daoimpl.mybatis.CommonDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.server.config.ServerSettings;
import net.thumbtack.school.hiring.utils.DaoType;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import net.thumbtack.school.hiring.utils.Settings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJerseyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJerseyServer.class);
    private final CommonDaoImpl commonDao = new CommonDaoImpl();

    private static final Settings settings = Settings.getInstance();

    protected static ServerClient client = new ServerClient();
    private static String baseURL;

    private static void setBaseUrl() {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.debug("Can't determine my own host name", e);
        }
        baseURL = "http://" + host + ":" + ServerSettings.getRestHTTPPort() + "/api";
    }

    @BeforeAll
    public static void startServer() {
        setBaseUrl();
        HiringServer.createServer();
    }

    @BeforeAll
    public static void setUp() {
        MyBatisUtils.initSqlSessionFactory();
        settings.setDaoType(DaoType.SQL);
    }

    @AfterAll
    public static void stopServer() {
        HiringServer.stopServer();
    }

    @BeforeEach
    public void clearDataBase() {
        commonDao.clear();
    }

    public static String getBaseURL() {
        return baseURL;
    }

    protected void checkFailureResponse(Object response, ErrorCode expectedStatus) {
        assertTrue(response instanceof ErrorDtoResponse);
        ErrorDtoResponse failureResponseObject = (ErrorDtoResponse) response;
        assertEquals(expectedStatus, failureResponseObject.getErrorCode());
    }

    private void registerEmployee(String email, String surname, String name, String patronymic, String login, String password, ErrorCode code) {
        RegisterEmployeeDtoRequest request = new RegisterEmployeeDtoRequest(email, surname, name, patronymic, login, password);
        Object response = client.post(baseURL + "/hiring/employee", request, RegisterDtoResponse.class, "");
        if (response instanceof RegisterDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }


    private void registerEmployer(String title, String address, String email, String surname, String name, String patronymic, String login, String password, ErrorCode code) {
        RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(email, surname, name, patronymic,login, password, title, address);
        Object response = client.post(baseURL + "/hiring/employer", request, RegisterDtoResponse.class, "");
        if (response instanceof RegisterDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void updateEmployee(String email, String surname, String name, String patronymic, String login, String password, boolean status, String token, ErrorCode code) {
        UpdateEmployeeDtoRequest request = new UpdateEmployeeDtoRequest(email, surname, name, patronymic, login, password, status, token);
        Object response = client.post(baseURL + "/hiring/employee/update/" + token, request, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void updateEmployer(String title, String address, String email, String surname, String name, String patronymic, String login, String password, String token, ErrorCode code) {
        UpdateEmployerDtoRequest request = new UpdateEmployerDtoRequest(email, surname, name, patronymic, login, password, title, address, token);
        Object response = client.post(baseURL + "/hiring/employer/update/" + token, request, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void removeEmployee(String token, ErrorCode code) {
        Object response = client.delete(baseURL + "/hiring/employee/remove/" + token, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void removeEmployer(String token, ErrorCode code) {
        Object response = client.delete(baseURL + "/hiring/employer/remove/" + token, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private LoginDtoResponse loginUser(String login, String password, ErrorCode code) {
        LoginDtoRequest request = new LoginDtoRequest(login, password);
        Object response = client.post(baseURL + "/hiring/user/login", request, LoginDtoResponse.class, "");
        if (response instanceof LoginDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
            return (LoginDtoResponse) response;
        } else {
            checkFailureResponse(response, code);
            return null;
        }
    }

    private void logoutUser(String token, ErrorCode code) {
        Object response = client.delete(baseURL + "/hiring/user/logout/" + token, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private AppendSkillDtoResponse addSkill(String token, String description, int level, ErrorCode code) {
        AppendSkillDtoRequest addSkillDtoRequest = new AppendSkillDtoRequest(description, level);
        Object response = client.post(baseURL + "/hiring/employee/skill", addSkillDtoRequest, AppendSkillDtoResponse.class, token);
        if (response instanceof AppendSkillDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
            return (AppendSkillDtoResponse) response;
        } else {
            checkFailureResponse(response, code);
            return null;
        }
    }

    private void updateSkill(String token, int skillId, String description, int level, ErrorCode code) {
        UpdateSkillDtoRequest request = new UpdateSkillDtoRequest(skillId, description, level, token);
        Object response = client.post(baseURL + "/hiring/employee/skill/update/" + skillId, request, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void deleteSkill(String token, int skillId, ErrorCode code) {
        Object response = client.delete(baseURL + "/hiring/employee/skill/remove/" + skillId, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private AppendVacancyDtoResponse addVacancy(String token, String jobTitle, int salary, ErrorCode code) {
        AppendVacancyDtoRequest addVacancyDtoRequest = new AppendVacancyDtoRequest(jobTitle, salary);
        Object response = client.post(baseURL + "/hiring/employer/vacancy", addVacancyDtoRequest, AppendVacancyDtoResponse.class, token);
        if (response instanceof AppendVacancyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
            return (AppendVacancyDtoResponse) response;
        } else {
            checkFailureResponse(response, code);
            return null;
        }
    }

    private void updateVacancy(String token, int vacancyId, String jobTitle, int salary, boolean status, ErrorCode code) {
        UpdateVacancyDtoRequest request = new UpdateVacancyDtoRequest(vacancyId, jobTitle, salary, status, token);
        Object response = client.post(baseURL + "/hiring/employer/vacancy/update/" + vacancyId, request, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void deleteVacancy(String token, int vacancyId, ErrorCode code) {
        Object response = client.delete(baseURL + "/hiring/employer/vacancy/remove/" + vacancyId, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private AppendVacancyRequirementDtoResponse addVacancyRequirement(String token, int vacancyId, String description, int level, boolean obligatory, ErrorCode code) {
        AppendVacancyRequirementDtoRequest addRequirementDtoRequest = new AppendVacancyRequirementDtoRequest(vacancyId, description, level, obligatory);
        Object response = client.post(baseURL + "/hiring/employer/vacancy/vacancy_requirement", addRequirementDtoRequest, AppendVacancyRequirementDtoResponse.class, token);
        if (response instanceof AppendVacancyRequirementDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
            return (AppendVacancyRequirementDtoResponse) response;
        } else {
            checkFailureResponse(response, code);
            return null;
        }
    }

    private void updateVacancyRequirement( String token, int vacancyRequirementId, String description, int level, boolean obligatory, int vacancyId, ErrorCode code) {
        UpdateVacancyRequirementDtoRequest request = new UpdateVacancyRequirementDtoRequest(vacancyRequirementId, description, level, obligatory, vacancyId);
        Object response = client.post(baseURL + "/hiring/employer/vacancy/vacancy_requirement/update/" + vacancyRequirementId, request, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }

    private void deleteVacancyRequirement(String token, int vacancyRequirementId, ErrorCode code) {
        Object response = client.delete(baseURL + "/hiring/employer/vacancy/vacancy_requirement/remove/" + vacancyRequirementId, EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, code);
        } else {
            checkFailureResponse(response, code);
        }
    }



    @Test
    public void testRegisterEmployee() {
        registerEmployee("employee@mail.ru",
                "surname",
                "name",
                "patronymic",
                "login",
                "pass", ErrorCode.SUCCESS);
    }

    @Test
    public void testRegisterEmployer() {
        registerEmployer("title",
                "address",
                "employer@mail.ru",
                "surname",
                "name",
                "patronymic",
                "login",
                "pass", ErrorCode.SUCCESS);
    }

    @Test
    public void testLoginAndLogoutEmployee() {
        registerEmployee("employee@mail.ru",
                "surnameE",
                "nameE",
                "patronymicE",
                "loginE",
                "passE", ErrorCode.SUCCESS);

        String token = loginUser("loginE", "passE", ErrorCode.SUCCESS).getToken();

        logoutUser(token, ErrorCode.SUCCESS);
    }

    @Test
    public void testLoginAndLogoutEmployer() {
        registerEmployer("title", "address", "employer@mail.ru",
                "surnameR",
                "nameR",
                "patronymicR",
                "loginR",
                "passR", ErrorCode.SUCCESS);

        String token = loginUser("loginR", "passR", ErrorCode.SUCCESS).getToken();
        
        logoutUser(token, ErrorCode.SUCCESS);
    }

    @Test
    public void testUpdateEmployee() {
        registerEmployee("employee@mail.ru",
                "surnameE",
                "nameE",
                "patronymicE",
                "loginE",
                "passE", ErrorCode.SUCCESS);

        String token = loginUser("loginE", "passE", ErrorCode.SUCCESS).getToken();

        updateEmployee("another@mail.ru", "surnameA", "nameA", "patronymicA", "loginA",
                "password", true, token, ErrorCode.SUCCESS);
    }

    @Test
    public void testUpdateEmployer() {
        registerEmployer("title", "address", "employer@mail.ru",
                "surnameR",
                "nameR",
                "patronymicR",
                "loginR",
                "passR", ErrorCode.SUCCESS);

        String token = loginUser("loginR", "passR", ErrorCode.SUCCESS).getToken();

        updateEmployer("titleA","addressA", "another@mail.ru", "surnameA", "nameA", "patronymicA", "loginA",
                "passA", token, ErrorCode.SUCCESS);

    }

    @Test
    public void testRemoveEmployee() {
        registerEmployee("employee@mail.ru",
                "surnameE",
                "nameE",
                "patronymicE",
                "loginE",
                "passE", ErrorCode.SUCCESS);

        String token = loginUser("loginE", "passE", ErrorCode.SUCCESS).getToken();

        removeEmployee(token, ErrorCode.SUCCESS);
    }

    @Test
    public void testRemoveEmployer() {
        registerEmployer("title", "address", "employer@mail.ru",
                "surnameR",
                "nameR",
                "patronymicR",
                "loginR",
                "passR", ErrorCode.SUCCESS);

        String token = loginUser("loginR", "passR", ErrorCode.SUCCESS).getToken();

        removeEmployer(token, ErrorCode.SUCCESS);
    }

    @Test
    public void testEmployeeAddAndDeleteSkills() {
        registerEmployee("employee@mail.ru",
                "surnameE",
                "nameE",
                "patronymicE",
                "loginE",
                "passE", ErrorCode.SUCCESS);

        String token = loginUser("loginE", "passE", ErrorCode.SUCCESS).getToken();

        int idSkill = addSkill(token, "Python", 3, ErrorCode.SUCCESS).getId();

        deleteSkill(token, idSkill, ErrorCode.SUCCESS);
    }

    @Test
    public void testEmployeeUpdateSkill() {
        registerEmployee("employee@mail.ru",
                "surnameE",
                "nameE",
                "patronymicE",
                "loginE",
                "passE", ErrorCode.SUCCESS);

        String token = loginUser("loginE", "passE", ErrorCode.SUCCESS).getToken();

        int idSkill = addSkill(token, "Python", 3, ErrorCode.SUCCESS).getId();

        updateSkill(token, idSkill, "Java", 5, ErrorCode.SUCCESS);
    }

    @Test
    public void testEmployerAddAndDeleteVacanciesWithRequirements() {
        registerEmployer("title", "address", "employer@mail.ru",
                "surnameR",
                "nameR",
                "patronymicR",
                "loginR",
                "passR", ErrorCode.SUCCESS);

        String token = loginUser("loginR", "passR", ErrorCode.SUCCESS).getToken();

        int idVacancy = addVacancy(token, "Java Middle", 130_000, ErrorCode.SUCCESS).getId();
        int idVacancy2 = addVacancy(token, "Junior_Python", 30000, ErrorCode.SUCCESS).getId();

        int idVacancyRequirement = addVacancyRequirement(token, idVacancy, "Hibernate", 4, true, ErrorCode.SUCCESS).getId();
        int idVacancyRequirement1 = addVacancyRequirement(token, idVacancy, "Spring", 5, true, ErrorCode.SUCCESS).getId();
        int idVacancyRequirement2 = addVacancyRequirement(token, idVacancy2, "Data Science", 3, true, ErrorCode.SUCCESS).getId();

        deleteVacancyRequirement(token, idVacancyRequirement, ErrorCode.SUCCESS);
        deleteVacancy(token, idVacancy, ErrorCode.SUCCESS);
    }

    @Test
    public void testEmployerUpdateVacancy() {
        registerEmployer("title", "address", "employer@mail.ru",
                "surnameR",
                "nameR",
                "patronymicR",
                "loginR",
                "passR", ErrorCode.SUCCESS);

        String token = loginUser("loginR", "passR", ErrorCode.SUCCESS).getToken();

        int idVacancy = addVacancy(token, "Python Middle", 80_000, ErrorCode.SUCCESS).getId();

        updateVacancy(token, idVacancy, "Python Senior", 200_000, true, ErrorCode.SUCCESS);
    }

    @Test
    public void testEmployerUpdateVacancyRequirement() {
        registerEmployer("title", "address", "employer@mail.ru",
                "surnameR",
                "nameR",
                "patronymicR",
                "loginR",
                "passR", ErrorCode.SUCCESS);

        String token = loginUser("loginR", "passR", ErrorCode.SUCCESS).getToken();

        int idVacancy = addVacancy(token, "Python Middle", 80_000, ErrorCode.SUCCESS).getId();
        int idVacancyRequirement = addVacancyRequirement(token, idVacancy, "Django", 4, true, ErrorCode.SUCCESS).getId();

        updateVacancyRequirement(token, idVacancyRequirement, "Flask", 5, true, idVacancy, ErrorCode.SUCCESS);
    }
}
