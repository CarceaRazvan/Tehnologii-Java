package org.example.laboratorul2.compulsory;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "fileUploadServlet", value = "/file-upload")
public class FileUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("file");

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        Collections.shuffle(lines);

        HttpSession session = request.getSession();
        session.setAttribute("shuffledLines", lines);

        response.sendRedirect("result.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.sendRedirect("input.jsp");
    }

}
