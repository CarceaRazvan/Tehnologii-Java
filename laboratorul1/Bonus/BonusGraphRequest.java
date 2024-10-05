package org.example.compulsory.bonus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//ex3
public class BonusGraphRequest {

    private static final int NUM_THREADS = 50;
    private static final int NUM_REQUESTS = 1000;
    private static final int ORDER = 55;
    private static final int K = 100;

    private static final String URL_STRING = String.format("http://localhost:8080/compulsory-1.0-SNAPSHOT/bonus-servlet?order=%d&k=%d", ORDER, K);


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        long totalStartTime = System.currentTimeMillis();

        for (int i = 1; i <= NUM_REQUESTS; i++) {

            final int requestId = i;

            executorService.submit(() -> {

                try {
                    URL url = new URL(URL_STRING);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine).append("\n");
                        }

                        in.close();

//                        System.out.println("Request " + requestId + " - Spanning Trees:\n" + content);

                    } else {
                        System.out.println("Request " + requestId + " - Error: " + responseCode + " - Unable to fetch spanning trees.");
                    }


                } catch (IOException e) {

                    System.err.println("Request " + requestId + " - Exception occurred: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        long totalEndTime = System.currentTimeMillis();
        System.out.println("NUM_THREADS: "+ NUM_THREADS + ", NUM_REQUESTS: "+ NUM_REQUESTS + ", ORDER: "+ ORDER + ", K: " + K +", Time: " + (totalEndTime - totalStartTime) + " ms");
    }
}
