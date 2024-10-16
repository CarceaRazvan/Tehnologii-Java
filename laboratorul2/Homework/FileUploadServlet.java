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

        HttpSession session = request.getSession();
        Part filePart = request.getPart("file");

        List<String> lines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }


        Collections.shuffle(lines);
        session.setAttribute("shuffledLines", lines);
        response.sendRedirect("result.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.sendRedirect("input.jsp");
    }


}
