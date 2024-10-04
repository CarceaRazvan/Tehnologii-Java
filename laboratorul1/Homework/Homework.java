package org.example.compulsory;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.graph4j.Graph;
import org.graph4j.generators.RandomGnmGraphGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

// ex1
@WebServlet(name = "homeworkServlet", value = "/homework-servlet")
public class Homework extends HttpServlet {

    private static final Logger logger = Logger.getLogger("homework-servlet");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int numVertices = Integer.parseInt(request.getParameter("numVertices"));
        int numEdges = Integer.parseInt(request.getParameter("numEdges"));
        RandomGnmGraphGenerator generator = new RandomGnmGraphGenerator(numVertices, numEdges);
        Graph graph = generator.createGraph();

        int[][] adjacencyMatrix = graph.adjacencyMatrix();

        //  ex3
        String userAgent = request.getHeader("User-Agent");

        if (isDesktopClient(userAgent)) {

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    out.print(adjacencyMatrix[i][j] + " ");
                }
                out.println();
            }

        } else {

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Adjacency Matrix</title></head>");
            out.println("<body>");
            out.println("<h1>Adjacency Matrix</h1>");
            out.println("<table border='1'>");

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                out.println("<tr>");
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    out.println("<td>" + adjacencyMatrix[i][j] + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }

        // ex2
        logRequestDetails(request);

    }

    // ex2
    private void logRequestDetails(HttpServletRequest request) {

        String httpMethod = request.getMethod();
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String clientLanguages = request.getHeader("Accept-Language");

        Map<String, String[]> parameterMap = request.getParameterMap();

        StringBuilder logMessage = new StringBuilder();

        logMessage.append("\n").append("HTTP Method: ").append(httpMethod).append("\n")
                .append("Client IP Address: ").append(clientIp).append("\n")
                .append("User-Agent: ").append(userAgent).append("\n")
                .append("Client Languages: ").append(clientLanguages).append("\n")
                .append("Request Parameters: \n");


        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            logMessage.append(entry.getKey()).append(" = ")
                    .append(Arrays.toString(entry.getValue())).append("\n");
        }

        logger.info(logMessage.toString());
    }

    // ex3
    private boolean isDesktopClient(String userAgent) {
        if (userAgent == null) {
            return true;
        }

        String[] browserIdentifiers = { "Mozilla", "Chrome", "Safari", "Firefox", "Edge", "Edg" };

        for (String identifier : browserIdentifiers) {
            if (userAgent.contains(identifier)) {
                return false;
            }
        }

        return true;
    }
}
