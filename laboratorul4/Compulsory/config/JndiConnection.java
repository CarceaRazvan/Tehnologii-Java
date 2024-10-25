package com.example.lab3.compulsory.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JndiConnection {


    public static DataSource InitializeDataSource() {

        DataSource ds;

        try {
            InitialContext ctx = new javax.naming.InitialContext();
            ds = (javax.sql.DataSource)ctx.lookup("jdbc/sample");

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        return ds;
    }

}
