package com.demo.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "hello_invocations", description = "Number of invocations of sayHello method")
    @Timed(name = "hello_response_time", description = "Response time for sayHello method")
    public String sayHello() {
        return "Hello, World!";
    }
}