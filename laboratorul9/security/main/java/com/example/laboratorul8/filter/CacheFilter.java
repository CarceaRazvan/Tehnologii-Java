package com.example.laboratorul8.filter;

import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Provider
@Singleton
public class CacheFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Map<String, Response> cache = new HashMap<>();

    public CacheFilter() {
        System.out.println("CacheFilter initialized");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        System.out.println("Processing request: " + requestContext.getUriInfo().getRequestUri());

        if (requestContext.getMethod().equals("GET") && requestContext.getUriInfo().getPath().contains("evaluations/list")) {

            String cacheKey = createCacheKey(requestContext);
            Response cachedResponse = cache.get(cacheKey);

            if (cachedResponse != null) {
                System.out.println("Cache hit for key: " + cacheKey);
                requestContext.abortWith(cachedResponse);
            } else {
                System.out.println("Cache miss for key: " + cacheKey);
            }
        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        System.out.println("Processing response for: " + requestContext.getUriInfo().getRequestUri());

        if (requestContext.getMethod().equals("GET") && requestContext.getUriInfo().getPath().contains("evaluations/list") && responseContext.getStatus() == 200) {
            String cacheKey = createCacheKey(requestContext);
            Response cachedResponse = Response.status(responseContext.getStatus())
                    .entity(responseContext.getEntity())
                    .build();
            cache.put(cacheKey, cachedResponse);
            System.out.println("Caching response for key: " + cacheKey);
        }
    }

    private String createCacheKey(ContainerRequestContext requestContext) {
        String teacherUsername = requestContext.getUriInfo().getQueryParameters().getFirst("teacherUsername");
        String role = requestContext.getUriInfo().getQueryParameters().getFirst("role");
        return "teacherUsername=" + teacherUsername + "&role=" + role;
    }

    public static void invalidateCache() {
        cache.clear();
    }

    public static void deleteCacheByKey(String username, String role) {

        String cacheKey = "teacherUsername=" + username + "&role=" + role;
        cache.remove(cacheKey);
    }
}

