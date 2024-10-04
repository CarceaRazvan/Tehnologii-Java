package org.example.compulsory;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class Compulsory extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputString = request.getParameter("input");

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Character List</title></head>");
            out.println("<body>");
            out.println("<h1>Character List</h1>");

            if (inputString != null && !inputString.isEmpty()) {
                out.println("<ol>");
                for (char c : inputString.toCharArray()) {
                    out.println("<li>" + c + "</li>");
                }
                out.println("</ol>");
            } else {
                out.println("<p>No input string provided!</p>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }
}