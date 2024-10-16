package org.example.laboratorul2.compulsory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.laboratorul2.bonus.EdgeColoring;
import org.graph4j.Edge;
import org.graph4j.Graph;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@WebServlet(name = "fileUploadServlet", value = "/file-upload")
public class FileUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userCaptcha = request.getParameter("captchaInput");

        String sessionCaptcha = (String) request.getSession().getAttribute("captcha");

        if (sessionCaptcha == null || !sessionCaptcha.equals(userCaptcha)) {
            response.getWriter().write("Captcha validation failed.");
            return;
        }

        Set<String> nodes = new HashSet<>();
        List<String> edges = new ArrayList<>();


        boolean isGraphFile = false;
        int numberOfColors;

        HttpSession session = request.getSession();
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Safe extraction

        if (fileName.contains("col")) {
            isGraphFile = true;
        }

        List<String> lines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }


        if(!isGraphFile) {

            Collections.shuffle(lines);
            session.setAttribute("shuffledLines", lines);
            response.sendRedirect("result.jsp");

        } else {


            List<String> linesColoring = new ArrayList<>();

            EdgeColoring edgeColoring = new EdgeColoring(lines);
            Graph graph = edgeColoring.loadGraphFromDIMACS();
            Map<Edge, Integer> edgeColors = edgeColoring.greedyColorEdges(graph);

            // culorile sunt indexate de la 0
            numberOfColors = edgeColoring.totalColors + 1;

            linesColoring.add("s col " + numberOfColors + '\n');

            for (Map.Entry<Edge, Integer> entry : edgeColors.entrySet()) {

                Edge edge = entry.getKey();
                int color = entry.getValue();

                linesColoring.add("l " + edge.toString() + " " + color + '\n');

                String node1 = String.valueOf(edge.source());
                String node2 = String.valueOf(edge.target());

                nodes.add(node1);
                nodes.add(node2);

                edges.add(node1 + "," + node2 + "," + color);
            }

            session.setAttribute("linesColoring", linesColoring);
            session.setAttribute("colors", numberOfColors);
            session.setAttribute("nodes", nodes);
            session.setAttribute("edges", edges);
            response.sendRedirect("graph-displayed.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.sendRedirect("input.jsp");
    }


}
