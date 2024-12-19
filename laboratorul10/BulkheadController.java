package com.demo.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Path("/bulkhead")
@ApplicationScoped
public class BulkheadController {

    @Bulkhead(value = 5, waitingTaskQueue = 8)
    @Fallback(fallbackMethod = "fallbackMethod")
    @GET
    public Response simulateBulkhead() {
        CompletableFuture<Response> futureResponse = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Simulating long-running task...");

                Thread.sleep(1000);

                System.out.println("Task completed successfully.");
                return Response.ok("Bulkhead executed successfully").build();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Execution interrupted: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Execution interrupted: " + e.getMessage()).build();
            }
        });

        try {
            return futureResponse.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Execution was interrupted: " + e.getMessage()).build();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof BulkheadException) {
                System.out.println("BulkheadException: Too many concurrent requests.");
                return Response.status(Response.Status.TOO_MANY_REQUESTS)
                        .entity("BulkheadException: Too many concurrent requests. Please try again later.")
                        .build();
            }
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error executing the request: " + e.getCause().getMessage())
                    .build();
        }
    }

    public Response fallbackMethod() {
        System.out.println("Bulkhead reached the limit. Returning fallback response.");
        return Response.status(Response.Status.TOO_MANY_REQUESTS)
                .entity("Too many concurrent requests. Please try again later.")
                .build();
    }
}

