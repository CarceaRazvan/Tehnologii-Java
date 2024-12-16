package com.demo.rest;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class HelloWorldClient {

    public String callHelloWorldService() {
        // Create a client to make HTTP requests
        Client client = ClientBuilder.newClient();

        // Target URL of the server's hello endpoint
        WebTarget target = client.target("http://localhost:9080/micro-openliberty/api/hello"); // Point to server's endpoint

        try {
            // Make the GET request to the hello world service
            Response response = target.request().method(HttpMethod.GET);

            // Check if the response was successful (HTTP 200)
            if (response.getStatus() == 200) {
                return response.readEntity(String.class); // Read and return the response body
            } else {
                return "Error: " + response.getStatus(); // Return an error message if status is not 200
            }
        } catch (ClientErrorException e) {
            // Catch any errors during the request
            return "Error calling service: " + e.getMessage();
        }
    }
}