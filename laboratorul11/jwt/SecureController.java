package com.demo.rest.laboratorul11.jwt;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.security.Principal;

@Path("ping")
public class SecureController {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    Principal principal;

    @Inject
    @Claim("administrator_id")
    private JsonNumber administrator_id;

    @GET
    @RolesAllowed("admin")
    public Response ping() {
        System.out.println("Secret book for " + principal.getName()
                + " with roles " + jsonWebToken.getGroups());
        System.out.println("Administrator level: "
                + jsonWebToken.getClaim("administrator_level").toString());
        System.out.println("Administrator id: " + administrator_id);

        JsonObject secretBook = Json.createObjectBuilder()
                .add("title", "secret")
                .add("author", "duke")
                .build();

        return Response.ok(secretBook).build();
    }
}
