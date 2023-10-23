/*
package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.AppendSkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.LoginDtoResponse;
import net.thumbtack.school.hiring.dto.response.RegisterDtoResponse;
import net.thumbtack.school.hiring.error.ServerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployee {
    HiringServer server = new HiringServer();
    private static final Gson gson = new Gson();
    private static final int GOOD_CODE = 200;
    private static final int BAD_CODE = 400;

    @BeforeAll()
    public static void setUp() {
        HiringServer.init();
    }

    @BeforeEach()
    public void clearDatabase() {
        server.clear();
    }

    @Test
    public void testRegisterAndLoginEmployees() throws ServerException{
        RegisterEmployeeDtoRequest request = new RegisterEmployeeDtoRequest(
                "rakhov@mail.ru",
                "Rakhov",
                "Andrei",
                "Olegovich",
                "rakhov",
                "1234"
        );

        RegisterEmployeeDtoRequest request1 = new RegisterEmployeeDtoRequest(
                "sobaga@mail.ru",
                "Olegov",
                "Ivan",
                "Olegovich",
                "oleg",
                "1234"
        );

        ServerResponse serverResponse = server.registerEmployee(gson.toJson(request));
        ServerResponse serverResponse1 = server.registerEmployee(gson.toJson(request1));

        assertEquals(GOOD_CODE, serverResponse.getResponseCode());
        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "rakhov",
                "1234"
        );

        ServerResponse serverResponse2 = server.loginEmployee(gson.toJson(loginDtoRequest));
        assertEquals(GOOD_CODE, serverResponse2.getResponseCode());

        LoginDtoRequest loginDtoRequest1 = new LoginDtoRequest(
                "oleg",
                "1234"
        );


        ServerResponse serverResponse3 = server.loginEmployee(gson.toJson(loginDtoRequest1));
        assertEquals(GOOD_CODE, serverResponse3.getResponseCode());
    }


    @Test
    public void testRegisterWithNullParameters() throws ServerException {
        RegisterEmployeeDtoRequest badRequest1 = new RegisterEmployeeDtoRequest(
                "",
                "Olegov",
                "Ivan",
                "Olegovich",
                "oleg",
                "1234"
        );

        RegisterEmployeeDtoRequest badRequest2 = new RegisterEmployeeDtoRequest(
                "someemail@mail.ru",
                null,
                "Ivan",
                "Olegovich",
                "oleg",
                "1234"
        );

        RegisterEmployeeDtoRequest badRequest3 = new RegisterEmployeeDtoRequest(
                "someemail@mail.ru",
                "Olegov",
                "",
                "Olegovich",
                "oleg",
                "1234"
        );

        RegisterEmployeeDtoRequest goodRequest1 = new RegisterEmployeeDtoRequest(
                "someemail@mail.ru",
                "Olegov",
                "oleg",
                "",
                "oleg",
                "1234"
        );

        RegisterEmployeeDtoRequest badRequest4 = new RegisterEmployeeDtoRequest(
                "someemail@mail.ru",
                "Olegov",
                "oleg",
                "Olegovich",
                "",
                "1234"
        );

        RegisterEmployeeDtoRequest badRequest5 = new RegisterEmployeeDtoRequest(
                "someemail@mail.ru",
                "Olegov",
                "oleg",
                "Olegovich",
                "oleg",
                null
        );

        ServerResponse badServerResponse1 = server.registerEmployee(gson.toJson(badRequest1));
        ServerResponse badServerResponse2 = server.registerEmployee(gson.toJson(badRequest2));
        ServerResponse badServerResponse3 = server.registerEmployee(gson.toJson(badRequest3));
        ServerResponse goodServerResponse1 = server.registerEmployee(gson.toJson(goodRequest1));
        ServerResponse badServerResponse4 = server.registerEmployee(gson.toJson(badRequest4));
        ServerResponse badServerResponse5 = server.registerEmployee(gson.toJson(badRequest5));

        assertEquals(BAD_CODE, badServerResponse1.getResponseCode());
        assertEquals(BAD_CODE, badServerResponse2.getResponseCode());
        assertEquals(BAD_CODE, badServerResponse3.getResponseCode());
        assertEquals(GOOD_CODE, goodServerResponse1.getResponseCode());
        assertEquals(BAD_CODE, badServerResponse4.getResponseCode());
        assertEquals(BAD_CODE, badServerResponse5.getResponseCode());
    }


    @Test
    public void RegisterLoginAndLogoutEmployee() throws ServerException{
        RegisterEmployeeDtoRequest registerEmployee = new RegisterEmployeeDtoRequest(
                "rakhov@mail.ru",
                "Rakhov",
                "Andrei",
                "Olegovich",
                "rakhov",
                "1234"
        );

        server.registerEmployee(gson.toJson(registerEmployee));

        LoginDtoRequest loginEmployee = new LoginDtoRequest(
                "rakhov",
                "1234"
        );

        ServerResponse response2 = server.loginEmployee(gson.toJson(loginEmployee));

        LoginDtoResponse loginDtoRequest = gson.fromJson(response2.getResponseMessage(), LoginDtoResponse.class);

        LogoutDtoRequest logoutEmployee = new LogoutDtoRequest(
                loginDtoRequest.getToken()
        );

        ServerResponse response3 = server.logoutEmployee(gson.toJson(logoutEmployee));

        assertEquals(GOOD_CODE, response3.getResponseCode());

    }

    @Test
    public void testLoginWithoutRegister() throws ServerException{
        LoginDtoRequest request = new LoginDtoRequest(
                "thisLoginDoestExist",
                "hello"
        );

        ServerResponse serverResponse = server.loginEmployee(gson.toJson(request));

        assertEquals(BAD_CODE, serverResponse.getResponseCode());
    }


    @Test
    public void testUpdateEmployee() throws ServerException{
        RegisterEmployeeDtoRequest request = new RegisterEmployeeDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password"
        );

        ServerResponse serverResponse = server.registerEmployee(gson.toJson(request));

        RegisterDtoResponse register = gson.fromJson(serverResponse.getResponseMessage(), RegisterDtoResponse.class);

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse loginResponse = server.loginEmployee(gson.toJson(loginDtoRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class);

        UpdateEmployeeDtoRequest updateEmployeeDtoRequest = new UpdateEmployeeDtoRequest(
                register.getId(),
                "andrei@mail.ru",
                "Rakhov",
                "Andrei",
                "Olegovich",
                "login",
                "password",
                false,
                loginDtoResponse.getToken()
        );

        ServerResponse serverResponse1 = server.updateEmployee(gson.toJson(updateEmployeeDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());
    }


    @Test
    public void testRemoveEmployee() throws ServerException{
        RegisterEmployeeDtoRequest request = new RegisterEmployeeDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password"
        );

        ServerResponse serverResponse = server.registerEmployee(gson.toJson(request));

        RegisterDtoResponse register = gson.fromJson(serverResponse.getResponseMessage(), RegisterDtoResponse.class);

        RemoveEmployeeDtoRequest removeEmployeeDtoRequest = new RemoveEmployeeDtoRequest(
                register.getId()
        );

        ServerResponse serverResponse1 = server.removeEmployee(gson.toJson(removeEmployeeDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());
    }


    @Test
    public void testAppendSkillToEmployee() throws ServerException{
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password"
        );

        server.registerEmployee(gson.toJson(registerRequest));

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse loginResponse = server.loginEmployee(gson.toJson(loginRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class);

        AppendSkillDtoRequest addSkill = new AppendSkillDtoRequest(
                "Java",
                5
        );

        AppendSkillDtoRequest addSkill2 = new AppendSkillDtoRequest(
                "Python",
                3
        );

        ServerResponse serverResponse = server.addSkill(loginDtoResponse.getToken(), gson.toJson(addSkill));
        ServerResponse serverResponse1 = server.addSkill(loginDtoResponse.getToken(), gson.toJson(addSkill2));


        assertEquals(GOOD_CODE, serverResponse.getResponseCode());
        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());
    }


    @Test
    public void testDeleteSkillFromEmployee() throws ServerException{
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password"
        );

        server.registerEmployee(gson.toJson(registerRequest));

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse loginResponse = server.loginEmployee(gson.toJson(loginRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class);

        AppendSkillDtoRequest addSkill = new AppendSkillDtoRequest(
                "Java",
                5
        );

        ServerResponse serverResponse = server.addSkill(loginDtoResponse.getToken(), gson.toJson(addSkill));

        AppendSkillDtoResponse appendSkillDtoResponse = gson.fromJson(serverResponse.getResponseMessage(), AppendSkillDtoResponse.class);

        RemoveSkillDtoRequest removeSkillDtoRequest = new RemoveSkillDtoRequest(
                loginDtoResponse.getToken(),
                appendSkillDtoResponse.getId()
        );

        ServerResponse serverResponse1 = server.removeSkill(gson.toJson(removeSkillDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());
    }


    @Test
    public void testUpdateSkillFromEmployee() throws ServerException{
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password"
        );

        server.registerEmployee(gson.toJson(registerRequest));

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse loginResponse = server.loginEmployee(gson.toJson(loginRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class);

        AppendSkillDtoRequest addSkill = new AppendSkillDtoRequest(
                "Java",
                5
        );

        ServerResponse serverResponse = server.addSkill(loginDtoResponse.getToken(), gson.toJson(addSkill));

        AppendSkillDtoResponse appendSkillDtoResponse = gson.fromJson(serverResponse.getResponseMessage(), AppendSkillDtoResponse.class);

        UpdateSkillDtoRequest updateSkillDtoRequest = new UpdateSkillDtoRequest(
                appendSkillDtoResponse.getId(),
                "Java",
                2,
                loginDtoResponse.getToken()
        );

        ServerResponse serverResponse1 = server.updateSkill(gson.toJson(updateSkillDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());

        UpdateSkillDtoRequest updateSkillDtoRequest1 = new UpdateSkillDtoRequest(
                appendSkillDtoResponse.getId(),
                "Python",
                2,
                loginDtoResponse.getToken()
        );

        ServerResponse serverResponse2 = server.updateSkill(gson.toJson(updateSkillDtoRequest1));

        assertEquals(GOOD_CODE, serverResponse2.getResponseCode());
    }
}



*/
