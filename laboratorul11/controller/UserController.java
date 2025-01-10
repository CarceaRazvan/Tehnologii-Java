package com.demo.rest.laboratorul11.controller;

import com.demo.rest.laboratorul11.model.User;
import com.demo.rest.laboratorul11.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/user")
public class UserController {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public Response register(
            @QueryParam("username") String username,
            @QueryParam("password") String password
    ) {
        boolean success = userService.register(username, password);

        if (success) {
            return Response.status(Response.Status.CREATED).entity("User register successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed registering").build();
        }
    }

    @GET
    @Path("/login")
    public Response login(
            @QueryParam("username") String username,
            @QueryParam("password") String password
    ) throws ServletException {

        User user = userService.login(username, password);

        if (user != null) {

            return Response.status(Response.Status.ACCEPTED).entity("Succesfull login").build();

        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed login").build();

        }

    }

    @GET
    @Path("/getByUsername")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUsername(@QueryParam("username") String username) {

        User user = userService.getByUsername(username);

        if (user == null) {

            return Response.status(Response.Status.BAD_REQUEST).entity("Failed returning user").build();
        }

        return Response.ok(user).build();

    }

}
