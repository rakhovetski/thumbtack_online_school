package net.thumbtack.school.hiring.resources;

import net.thumbtack.school.hiring.service.EmployerService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api")
public class EmployerResource {
    private final EmployerService employerService = new EmployerService();

    @POST
    @Path("/hiring/employer")
    @Produces("application/json")
    public Response registerEmployer(String requestJson) {
        return employerService.registerEmployer(requestJson);
    }

    @POST
    @Path("/hiring/employer/update/{token}")
    @Produces("application/json")
    public Response updateEmployer(String requestJson, @PathParam("token") String token) {
        return employerService.updateEmployer(requestJson, token);
    }

    @DELETE
    @Path("/hiring/employer/remove/{token}")
    @Produces("application/json")
    public Response removeEmployer(@PathParam("token") String token) {
        return employerService.removeEmployer(token);
    }

    @POST
    @Path("/hiring/employer/vacancy")
    @Produces("application/json")
    public Response addVacancy(@HeaderParam("token") String token, String requestJson) {
        return employerService.addVacancy(token, requestJson);
    }

    @POST
    @Path("/hiring/employer/vacancy/update/{vacancyId}")
    @Produces("application/json")
    public Response updateVacancy(@HeaderParam("token") String token, String requestJson, @PathParam("vacancyId") int vacancyId) {
        return employerService.updateVacancy(token, requestJson, vacancyId);
    }

    @DELETE
    @Path("/hiring/employer/vacancy/remove/{vacancyId}")
    @Produces("application/json")
    public Response removeVacancy(@HeaderParam("token") String token, @PathParam("vacancyId") int vacancyId) {
        return employerService.removeVacancy(token, vacancyId);
    }

    @POST
    @Path("/hiring/employer/vacancy/vacancy_requirement")
    @Produces("application/json")
    public Response addVacancyRequirement(@HeaderParam("token") String token, String json) {
        return employerService.addVacancyRequirement(token, json);
    }

    @POST
    @Path("/hiring/employer/vacancy/vacancy_requirement/update/{vacancyRequirementId}")
    @Produces("application/json")
    public Response updateVacancyRequirement(String requestJson, @PathParam("vacancyRequirementId") int vacancyRequirementId) {
        return employerService.updateVacancyRequirement(requestJson, vacancyRequirementId);
    }

    @DELETE
    @Path("/hiring/employer/vacancy/vacancy_requirement/remove/{vacancyRequirementId}")
    @Produces("application/json")
    public Response removeVacancyRequirement(@HeaderParam("token") String token, @HeaderParam("vacancyId") int vacancyId,  @PathParam("vacancyRequirementId") int vacancyRequirementId) {
        return employerService.removeVacancyRequirement(token,vacancyId, vacancyRequirementId);
    }
}
