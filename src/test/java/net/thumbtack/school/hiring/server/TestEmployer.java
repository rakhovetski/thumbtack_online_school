/*
package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.error.ServerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployer {
    HiringServer server = new HiringServer();
    private static final Gson gson = new Gson();
    private static final int GOOD_CODE = 200;
    private static final int BAD_CODE = 400;


    @BeforeAll()
    public static void setUp() {
        HiringServer.init();
    }

   @BeforeEach
   public void clearDatabase() {
       server.clear();
   }


   @Test
   public void testRegisterAndLoginEmployer() throws ServerException {
       RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(
               "rakhov@mail.ru",
               "Rakhov",
               "Andrei",
               "Olegovich",
               "rakhov",
               "1234",
               "rakhovCompany",
               "Prigorodnya 9"
       );

       RegisterEmployerDtoRequest request1 = new RegisterEmployerDtoRequest(
               "sobaga@mail.ru",
               "Olegov",
               "Ivan",
               "Olegovich",
               "oleg",
               "1234",
               "Google",
               "New York"
       );

       ServerResponse serverResponse = server.registerEmployer(gson.toJson(request));
       ServerResponse serverResponse1 = server.registerEmployer(gson.toJson(request1));

       assertEquals(GOOD_CODE, serverResponse.getResponseCode());
       assertEquals(GOOD_CODE, serverResponse1.getResponseCode());

       LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
               "rakhov",
               "1234"
       );

       LoginDtoRequest loginDtoRequest1 = new LoginDtoRequest(
               "oleg",
               "1234"
       );

       ServerResponse serverResponse2 = server.loginEmployer(gson.toJson(loginDtoRequest));
       ServerResponse serverResponse3 = server.loginEmployer(gson.toJson(loginDtoRequest1));

       assertEquals(GOOD_CODE, serverResponse2.getResponseCode());
       assertEquals(GOOD_CODE, serverResponse3.getResponseCode());
   }


   @Test
   public void testRegisterWithNullParameters() throws ServerException {
       RegisterEmployerDtoRequest badRequest1 = new RegisterEmployerDtoRequest(
               "",
               "Olegov",
               "Ivan",
               "Olegovich",
               "oleg",
               "1234",
               "title",
               "address"
       );

       RegisterEmployerDtoRequest badRequest2 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               null,
               "Ivan",
               "Olegovich",
               "oleg",
               "1234",
               "title",
               "address"
       );

       RegisterEmployerDtoRequest badRequest3 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               "Olegov",
               "",
               "Olegovich",
               "oleg",
               "1234",
               "title",
               "address"
       );

       RegisterEmployerDtoRequest goodRequest1 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               "Olegov",
               "oleg",
               "",
               "oleg",
               "1234",
               "title",
               "address"
       );

       RegisterEmployerDtoRequest badRequest4 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               "Olegov",
               "oleg",
               "Olegovich",
               "",
               "1234",
               "title",
               "address"
       );

       RegisterEmployerDtoRequest badRequest5 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               "Olegov",
               "oleg",
               "Olegovich",
               "oleg",
               null,
               "title",
               "address"
       );

       RegisterEmployerDtoRequest badRequest6 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               "Olegov",
               "oleg",
               "Olegovich",
               "oleg",
               "pswd",
               "",
               "address"
       );

       RegisterEmployerDtoRequest badRequest7 = new RegisterEmployerDtoRequest(
               "someemail@mail.ru",
               "Olegov",
               "oleg",
               "Olegovich",
               "oleg",
               "pswd",
               "title",
               ""
       );

       ServerResponse badServerResponse1 = server.registerEmployer(gson.toJson(badRequest1));
       ServerResponse badServerResponse2 = server.registerEmployer(gson.toJson(badRequest2));
       ServerResponse badServerResponse3 = server.registerEmployer(gson.toJson(badRequest3));
       ServerResponse goodServerResponse1 = server.registerEmployer(gson.toJson(goodRequest1));
       ServerResponse badServerResponse4 = server.registerEmployer(gson.toJson(badRequest4));
       ServerResponse badServerResponse5 = server.registerEmployer(gson.toJson(badRequest5));
       ServerResponse badServerResponse6 = server.registerEmployer(gson.toJson(badRequest6));
       ServerResponse badServerResponse7 = server.registerEmployer(gson.toJson(badRequest7));

       assertEquals(BAD_CODE, badServerResponse1.getResponseCode());
       assertEquals(BAD_CODE, badServerResponse2.getResponseCode());
       assertEquals(BAD_CODE, badServerResponse3.getResponseCode());
       assertEquals(GOOD_CODE, goodServerResponse1.getResponseCode());
       assertEquals(BAD_CODE, badServerResponse4.getResponseCode());
       assertEquals(BAD_CODE, badServerResponse5.getResponseCode());
       assertEquals(BAD_CODE, badServerResponse6.getResponseCode());
       assertEquals(BAD_CODE, badServerResponse7.getResponseCode());
   }


    @Test
    public void RegisterLoginAndLogoutEmployer() throws ServerException{
        RegisterEmployerDtoRequest registerEmployer = new RegisterEmployerDtoRequest(
                "rakhov@mail.ru",
                "Rakhov",
                "Andrei",
                "Olegovich",
                "rakhov",
                "1234",
                "title",
                "address"
        );

        server.registerEmployer(gson.toJson(registerEmployer));

        LoginDtoRequest loginEmployer = new LoginDtoRequest(
                "rakhov",
                "1234"
        );

        ServerResponse response2 = server.loginEmployer(gson.toJson(loginEmployer));

        LoginDtoResponse loginDtoResponse = gson.fromJson(response2.getResponseMessage(), LoginDtoResponse.class);

        LogoutDtoRequest logoutEmployer = new LogoutDtoRequest(
                loginDtoResponse.getToken()
        );

        ServerResponse response3 = server.logoutEmployer(gson.toJson(logoutEmployer));

        assertEquals(GOOD_CODE, response3.getResponseCode());
    }


    @Test
    public void testLoginWithoutRegister() throws ServerException{
        LoginDtoRequest request = new LoginDtoRequest(
                "thisLoginDoestExist",
                "hello"
        );

        ServerResponse serverResponse = server.loginEmployer(gson.toJson(request));

        assertEquals(BAD_CODE, serverResponse.getResponseCode());
    }


    @Test
    public void testUpdateEmployer() throws ServerException{
        RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password",
                "title",
                "address"
        );

        ServerResponse serverResponse = server.registerEmployer(gson.toJson(request));

        RegisterDtoResponse register = gson.fromJson(serverResponse.getResponseMessage(), RegisterDtoResponse.class);

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse response2 = server.loginEmployee(gson.toJson(loginDtoRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(response2.getResponseMessage(), LoginDtoResponse.class);


        UpdateEmployerDtoRequest updateEmployerDtoRequest = new UpdateEmployerDtoRequest(
                register.getId(),
                "andrei@mail.ru",
                "Rakhov",
                "Andrei",
                "Olegovich",
                "login",
                "password",
                "anotherTitle",
                "anotherAddress",
                loginDtoResponse.getToken()
        );

        ServerResponse serverResponse1 = server.updateEmployer(gson.toJson(updateEmployerDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());
    }


    @Test
    public void testRemoveEmployer() throws ServerException{
        RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password",
                "title",
                "address"
        );

        ServerResponse serverResponse = server.registerEmployer(gson.toJson(request));

        RegisterDtoResponse register = gson.fromJson(serverResponse.getResponseMessage(), RegisterDtoResponse.class);

        RemoveEmployerDtoRequest removeEmployerDtoRequest = new RemoveEmployerDtoRequest(
                register.getId()
        );

        ServerResponse serverResponse1 = server.removeEmployer(gson.toJson(removeEmployerDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());
    }


    @Test
    public void testAppendUpdateRemoveVacancyToEmployer() throws ServerException{
        RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password",
                "title",
                "address"
        );

        server.registerEmployer(gson.toJson(request));

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse loginResponse = server.loginEmployer(gson.toJson(loginRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class);

        AppendVacancyDtoRequest vacancyDtoRequest = new AppendVacancyDtoRequest(
                "Python backend",
                100_000
        );

        ServerResponse serverResponse1 = server.addVacancy(loginDtoResponse.getToken(),
                gson.toJson(vacancyDtoRequest));

        assertEquals(GOOD_CODE, serverResponse1.getResponseCode());

        AppendVacancyDtoResponse vacancyDtoResponse = gson.fromJson(serverResponse1.getResponseMessage(), AppendVacancyDtoResponse.class);

        UpdateVacancyDtoRequest updateVacancyDtoRequest = new UpdateVacancyDtoRequest(
                vacancyDtoResponse.getId(),
                "Python backend",
                200_000,
                true,
                loginDtoResponse.getToken()
        );

        ServerResponse serverResponse = server.updateVacancy(gson.toJson(updateVacancyDtoRequest));

        assertEquals(GOOD_CODE, serverResponse.getResponseCode());

        RemoveVacancyDtoRequest removeVacancyDtoRequest = new RemoveVacancyDtoRequest(
                vacancyDtoResponse.getId(),
                loginDtoResponse.getToken()
        );

        ServerResponse serverResponse2 = server.removeVacancy(gson.toJson(removeVacancyDtoRequest));

        assertEquals(GOOD_CODE, serverResponse2.getResponseCode());
    }



    @Test
    public void testAppendUpdateRemoveVacancyRequirementToVacancyToEmployer() throws ServerException{
        RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(
                "email",
                "surname",
                "name",
                "patronymic",
                "login",
                "password",
                "title",
                "address"
        );

        server.registerEmployer(gson.toJson(request));

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                "login",
                "password"
        );

        ServerResponse loginResponse = server.loginEmployer(gson.toJson(loginRequest));

        LoginDtoResponse loginDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class);

        AppendVacancyDtoRequest vacancyDtoRequest = new AppendVacancyDtoRequest(
                "Python backend",
                100_000
        );

        ServerResponse serverResponse1 = server.addVacancy(loginDtoResponse.getToken(),
                                                            gson.toJson(vacancyDtoRequest));


        AppendVacancyDtoResponse vacancyDtoResponse = gson.fromJson(serverResponse1.getResponseMessage(), AppendVacancyDtoResponse.class);

        AppendVacancyRequirementDtoRequest vacancyRequirementDtoRequest1 = new AppendVacancyRequirementDtoRequest(
                vacancyDtoResponse.getId(),
                "Django",
                5,
                true
        );

        AppendVacancyRequirementDtoRequest vacancyRequirementDtoRequest2 = new AppendVacancyRequirementDtoRequest(
                vacancyDtoResponse.getId(),
                "Flask",
                4,
                false
        );

        ServerResponse serverResponse3 = server.addVacancyRequirement(gson.toJson(vacancyRequirementDtoRequest1));
        ServerResponse serverResponse4 = server.addVacancyRequirement(gson.toJson(vacancyRequirementDtoRequest2));


        //Append VacancyRequirement tests
        assertEquals(GOOD_CODE, serverResponse3.getResponseCode());
        assertEquals(GOOD_CODE, serverResponse4.getResponseCode());


        AppendVacancyRequirementDtoResponse appendResponse1 = gson.fromJson(serverResponse3.getResponseMessage(),
                                                                            AppendVacancyRequirementDtoResponse.class);
        AppendVacancyRequirementDtoResponse appendResponse2 = gson.fromJson(serverResponse4.getResponseMessage(),
                                                                            AppendVacancyRequirementDtoResponse.class);


        UpdateVacancyRequirementDtoRequest updateVacancyRequirement1 = new UpdateVacancyRequirementDtoRequest(
                appendResponse1.getId(),
                "Django",
                3,
                false,
                vacancyDtoResponse.getId()
        );
        UpdateVacancyRequirementDtoRequest updateVacancyRequirement2 = new UpdateVacancyRequirementDtoRequest(
                appendResponse2.getId(),
                "Flask",
                5,
                true,
                vacancyDtoResponse.getId()
        );


        ServerResponse serverResponse5 = server.updateVacancyRequirement(gson.toJson(updateVacancyRequirement1));
        ServerResponse serverResponse6 = server.updateVacancyRequirement(gson.toJson(updateVacancyRequirement2));


        //Update vacancyRequirement tests
        assertEquals(GOOD_CODE, serverResponse5.getResponseCode());
        assertEquals(GOOD_CODE, serverResponse6.getResponseCode());


        RemoveVacancyRequirementDtoRequest removeVacancyRequirement1 = new RemoveVacancyRequirementDtoRequest(
                appendResponse1.getId(),
                vacancyDtoResponse.getId()
        );
        RemoveVacancyRequirementDtoRequest removeVacancyRequirement2 = new RemoveVacancyRequirementDtoRequest(
                appendResponse2.getId(),
                vacancyDtoResponse.getId()
        );


        ServerResponse serverResponse7 = server.removeVacancyRequirement(gson.toJson(removeVacancyRequirement1));
        ServerResponse serverResponse8 = server.removeVacancyRequirement(gson.toJson(removeVacancyRequirement2));


        //Remove vacancyRequirement tests
        assertEquals(GOOD_CODE, serverResponse7.getResponseCode());
        assertEquals(GOOD_CODE, serverResponse8.getResponseCode());
    }



    @Test
    public void testListEmployeesSuitableVacancyLevelNotLower() throws ServerException{
        // Создание работодателя с ваканскией
        RegisterEmployerDtoRequest registerEmployer = new RegisterEmployerDtoRequest(
                "employer@mail.ru",
                "employerSurname",
                "employerName",
                "employerPatronymic",
                "employerLogin",
                "1234",
                "employerTitle",
                "employerAddress"
        );

        server.registerEmployer(gson.toJson(registerEmployer));

        LoginDtoRequest loginEmployer = new LoginDtoRequest(
                "employerLogin",
                "1234"
        );

        ServerResponse loginResponse = server.loginEmployer(gson.toJson(loginEmployer));

        String loginTokenDtoResponse = gson.fromJson(loginResponse.getResponseMessage(), LoginDtoResponse.class).getToken();

        AppendVacancyDtoRequest vacancyDtoRequest = new AppendVacancyDtoRequest(
                "Python backend",
                100_000
        );

        AppendVacancyDtoRequest vacancyDtoRequest1 = new AppendVacancyDtoRequest(
                "Java",
                100_000
        );

        server.addVacancy(loginTokenDtoResponse,
                gson.toJson(vacancyDtoRequest));

        server.addVacancy(loginTokenDtoResponse,
                gson.toJson(vacancyDtoRequest1));

        VacancyNameDtoRequest vacancyNameDtoRequest = new VacancyNameDtoRequest(vacancyDtoRequest.getJobTitle());
        VacancyNameDtoRequest vacancyNameDtoRequest1 = new VacancyNameDtoRequest(vacancyDtoRequest1.getJobTitle());

        ServerResponse vacancyIdServerResponse = server.getIdVacancyByName(gson.toJson(vacancyNameDtoRequest));
        ServerResponse vacancyIdServerResponse1 = server.getIdVacancyByName(gson.toJson(vacancyNameDtoRequest1));

        int vacancyId = gson.fromJson(vacancyIdServerResponse.getResponseMessage(),
                VacancyIdDtoResponse.class).getId();
        int vacancyId1 = gson.fromJson(vacancyIdServerResponse1.getResponseMessage(),
                VacancyIdDtoResponse.class).getId();


        AppendVacancyRequirementDtoRequest vacancyRequirementDtoRequest1 = new AppendVacancyRequirementDtoRequest(
                vacancyId,
                "Django",
                5,
                true
        );

        AppendVacancyRequirementDtoRequest vacancyRequirementDtoRequest2 = new AppendVacancyRequirementDtoRequest(
                vacancyId,
                "Flask",
                3,
                false
        );

        AppendVacancyRequirementDtoRequest vacancyRequirementDtoRequest3 = new AppendVacancyRequirementDtoRequest(
                vacancyId1,
                "Hibernate",
                5,
                true
        );

        AppendVacancyRequirementDtoRequest vacancyRequirementDtoRequest4 = new AppendVacancyRequirementDtoRequest(
                vacancyId1,
                "Mock",
                3,
                false
        );

        server.addVacancyRequirement(gson.toJson(vacancyRequirementDtoRequest1));
        server.addVacancyRequirement(gson.toJson(vacancyRequirementDtoRequest2));

        server.addVacancyRequirement(gson.toJson(vacancyRequirementDtoRequest3));
        server.addVacancyRequirement(gson.toJson(vacancyRequirementDtoRequest4));


        //Создание работников со скиллами
        //1 - должен пройти
        RegisterEmployeeDtoRequest registerEmployee = new RegisterEmployeeDtoRequest(
                "employee@mail.ru",
                "employeeSurname",
                "employeeName",
                "employeePatronymic",
                "employeeLogin",
                "1234"
        );

        server.registerEmployee(gson.toJson(registerEmployee));

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                "employeeLogin",
                "1234"
        );

        ServerResponse loginResponse1 = server.loginEmployee(gson.toJson(loginRequest));

        String loginTokenDtoResponse1 = gson.fromJson(loginResponse1.getResponseMessage(), LoginDtoResponse.class).getToken();

        AppendSkillDtoRequest appendSkillDtoRequestForFirst1 = new AppendSkillDtoRequest(
                "Django",
                5
        );
        AppendSkillDtoRequest appendSkillDtoRequestForFirst2 = new AppendSkillDtoRequest(
                "Flask",
                4
        );
        AppendSkillDtoRequest appendSkillDtoRequestForFirst3 = new AppendSkillDtoRequest(
                "requests",
                5
        );




        server.addSkill(loginTokenDtoResponse1, gson.toJson(appendSkillDtoRequestForFirst1));
        server.addSkill(loginTokenDtoResponse1, gson.toJson(appendSkillDtoRequestForFirst2));
        server.addSkill(loginTokenDtoResponse1, gson.toJson(appendSkillDtoRequestForFirst3));


        //2 - не должен пройти
        RegisterEmployeeDtoRequest registerEmployee2 = new RegisterEmployeeDtoRequest(
                "employee2@mail.ru",
                "employee2Surname",
                "employee2Name",
                "employee2Patronymic",
                "employee2Login",
                "1234"
        );

        server.registerEmployee(gson.toJson(registerEmployee2));

        LoginDtoRequest loginRequest2 = new LoginDtoRequest(
                "employee2Login",
                "1234"
        );

        ServerResponse loginResponse2 = server.loginEmployee(gson.toJson(loginRequest2));

        String loginTokenDtoResponse2 = gson.fromJson(loginResponse2.getResponseMessage(), LoginDtoResponse.class).getToken();

        AppendSkillDtoRequest appendSkillDtoRequestForSecond1 = new AppendSkillDtoRequest(
                "Django",
                3
        );
        AppendSkillDtoRequest appendSkillDtoRequestForSecond2 = new AppendSkillDtoRequest(
                "Flask",
                5
        );


        server.addSkill(loginTokenDtoResponse2, gson.toJson(appendSkillDtoRequestForSecond1));
        server.addSkill(loginTokenDtoResponse2, gson.toJson(appendSkillDtoRequestForSecond2));


        //3 - не должен пройти
        RegisterEmployeeDtoRequest registerEmployee3 = new RegisterEmployeeDtoRequest(
                "employee3@mail.ru",
                "employee3Surname",
                "employee3Name",
                "employee3Patronymic",
                "employee3Login",
                "1234"
        );

        server.registerEmployee(gson.toJson(registerEmployee3));

        LoginDtoRequest loginRequest3 = new LoginDtoRequest(
                "employee3Login",
                "1234"
        );

        ServerResponse loginResponse3 = server.loginEmployee(gson.toJson(loginRequest3));

        String loginTokenDtoResponse3 = gson.fromJson(loginResponse3.getResponseMessage(), LoginDtoResponse.class).getToken();

        AppendSkillDtoRequest appendSkillDtoRequestForThird1 = new AppendSkillDtoRequest(
                "Pandas",
                5
        );
        AppendSkillDtoRequest appendSkillDtoRequestForThird2 = new AppendSkillDtoRequest(
                "Numpy",
                4
        );
        AppendSkillDtoRequest appendSkillDtoRequestForThird3 = new AppendSkillDtoRequest(
                "Matplotlib",
                5
        );

        server.addSkill(loginTokenDtoResponse3, gson.toJson(appendSkillDtoRequestForThird1));
        server.addSkill(loginTokenDtoResponse3, gson.toJson(appendSkillDtoRequestForThird2));
        server.addSkill(loginTokenDtoResponse3, gson.toJson(appendSkillDtoRequestForThird3));


        ServerResponse serverResponse = server.listEmployeesSuitableVacancyLevelNotLower(gson.toJson(
                                                                                    new VacancyIdDtoRequest(vacancyId)));

        assertEquals(GOOD_CODE, serverResponse.getResponseCode());

        ListOfEmployeesDtoResponse list = gson.fromJson(serverResponse.getResponseMessage(), ListOfEmployeesDtoResponse.class);

        for(EmployeeDtoResponse response : list.getEmployeeList()) {
            System.out.println(response);
        }
    }
}
*/
