package com.example.laboratorul8.init;


import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;


public class DataInitializer implements ServletContextListener {

    @Inject
    private DataInitializerBean dataInitializerBean;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dataInitializerBean.initData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}