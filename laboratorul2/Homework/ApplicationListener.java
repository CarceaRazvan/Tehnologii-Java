package org.example.laboratorul2.homework;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Date;

@WebListener
public class ApplicationListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent ce) {

        ServletContext context = ce.getServletContext();

        String prelude = context.getInitParameter("prelude");
        String coda =  context.getInitParameter("coda");


        prelude = prelude.replace("${java.version}", System.getProperty("java.version"));
        coda = coda.replace("${time}", new Date().toString());

        context.setAttribute("prelude", prelude);
        context.setAttribute("coda", coda);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application is shutting down.");
    }


}
