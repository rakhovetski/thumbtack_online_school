package net.thumbtack.school.hiring.resources;

import net.thumbtack.school.hiring.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api")
public class UserResource {
    private final UserService userService = new UserService();

    @POST
    @Path("/hiring/user/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginUser(String request) {
        return userService.login(request);
    }

    @DELETE
    @Path("/hiring/user/logout/{token}")
    @Produces("application/json")
    public Response logoutUser(@HeaderParam("token") String token) {
        return userService.logout(token);
    }
}
