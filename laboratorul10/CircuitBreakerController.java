package com.demo.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

@Path("/circuit")
@ApplicationScoped
public class CircuitBreakerController {

    private int failureCount = 0;

    @CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    @GET
    public Response simulateCircuitBreaker() {
        try {
            if (shouldFail()) {
                throw new RuntimeException("Simulated failure");
            }
            return Response.ok("Success").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage())
                    .build();
        }
    }

    private boolean shouldFail() {
        failureCount++;
        return failureCount % 4 != 0;
    }
}
