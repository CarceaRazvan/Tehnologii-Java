package com.example.lab3.compulsory.service;

import com.example.lab3.compulsory.dao.ProductDAO;
import com.example.lab3.compulsory.dao.ProductLogDAO;

import java.sql.SQLException;

public class ProductLogsService {

    private ProductLogDAO productLogDAO = new ProductLogDAO();

    public void insertLog(String user, String description) {

        productLogDAO.insertModificationLog(user, description);
    }


    public String getLastModification () {

        return productLogDAO.getLastModificationLog();
    }


}
