package com.example.laboratorul8.config;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;

@Path("/openapi")
public class SwaggerResource {

    @GET
    @Path("/swagger.json")
    @Produces("application/json")
    public Response getSwaggerJson() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("swagger.json");
        if (inputStream == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(inputStream).build();
    }
}