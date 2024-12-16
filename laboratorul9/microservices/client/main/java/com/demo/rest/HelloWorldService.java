package com.demo.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/call-hello")
public class HelloWorldService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String callHelloWorldService() {
        HelloWorldClient client = new HelloWorldClient();
        return "From server we got: " + client.callHelloWorldService();
    }
}