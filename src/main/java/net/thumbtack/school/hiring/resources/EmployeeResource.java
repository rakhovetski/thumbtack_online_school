package net.thumbtack.school.hiring.resources;

import net.thumbtack.school.hiring.service.EmployeeService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api")
public class EmployeeResource {
    private final EmployeeService employeeService = new EmployeeService();

    @POST
    @Path("/hiring/employee")
    @Produces("application/json")
    public Response registerEmployee(String requestJson) {
        return employeeService.registerEmployee(requestJson);
    }

    @POST
    @Path("/hiring/employee/update/{token}")
    @Produces("application/json")
    public Response updateEmployee(String requestJson, @PathParam("token") String token) {
        return employeeService.updateEmployee(requestJson, token);
    }

    @DELETE
    @Path("/hiring/employee/remove/{token}")
    @Produces("application/json")
    public Response removeEmployee(@PathParam("token") String token) {
        return employeeService.removeEmployee(token);
    }

    @POST
    @Path("/hiring/employee/skill")
    @Produces("application/json")
    public Response addSkill(@HeaderParam("token") String token, String requestJson) {
        return employeeService.addSkill(token, requestJson);
    }

    @POST
    @Path("/hiring/employee/skill/update/{skillId}")
    @Produces("application/json")
    public Response updateSkill(@HeaderParam("token") String token, String requestJson, @PathParam("skillId") int skillId) {
        return employeeService.updateSkill(token, requestJson, skillId);
    }

    @DELETE
    @Path("/hiring/employee/skill/remove/{skillId}")
    @Produces("application/json")
    public Response removeSkill(@HeaderParam("token") String token, @PathParam("skillId") int skillId) {
        return employeeService.removeSkill(token, skillId);
    }
}
