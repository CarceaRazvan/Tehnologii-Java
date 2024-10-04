package org.example.compulsory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GraphRequest {

    public static void main(String[] args) throws IOException {

        String urlString = "http://localhost:8080/compulsory-1.0-SNAPSHOT/homework-servlet?numVertices=6&numEdges=4";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }

        in.close();

        System.out.println("Adjacency Matrix:\n" + content);

    }
}
