package com.demo.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("/resilience")
@ApplicationScoped
public class FaultToleranceController {

    @Timeout(500)
    @Fallback(fallbackMethod = "fallbackForTimeout")
    @GET
    @Path("/timeout")
    public String doWork() {
        try {
            Thread.sleep(700L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Response from normal processing";
    }

    private String fallbackForTimeout() {
        return "Fallback answer due to timeout";
    }

    @Retry(maxRetries = 3, delay = 200, jitter = 50)
    @Fallback(fallbackMethod = "fallbackForRetry")
    @GET
    @Path("/retry")
    public String doWorkWithRetry() {
        throw new RuntimeException("Simulated service failure");
    }

    private String fallbackForRetry() {
        return "Fallback answer after retries failed";
    }
}