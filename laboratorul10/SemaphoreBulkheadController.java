package com.demo.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Path("/semaphore-bulkhead")
@ApplicationScoped
public class SemaphoreBulkheadController {

    private final Semaphore semaphore = new Semaphore(5);

    @GET
    public Response simulateBulkhead() {
        boolean permitAcquired = false;
        try {
            permitAcquired = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if (!permitAcquired) {
                System.out.println("Bulkhead limit reached. Too many concurrent requests.");
                return fallbackMethod();
            }

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

            return futureResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error executing the request: " + e.getMessage())
                    .build();
        } finally {
            if (permitAcquired) {
                semaphore.release();
            }
        }
    }

    public Response fallbackMethod() {
        System.out.println("Bulkhead reached the limit. Returning fallback response.");
        return Response.status(Response.Status.TOO_MANY_REQUESTS)
                .entity("Too many concurrent requests. Please try again later.")
                .build();
    }
}