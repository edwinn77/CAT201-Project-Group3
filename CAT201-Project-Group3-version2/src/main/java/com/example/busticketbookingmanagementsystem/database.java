package com.example.busticketbookingmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    public static Connection connectDb(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
                                                            //DATABASE LINK, USERNAME, PASSWORD RooT IS THE DEFAULT USERNAME
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/busdata", "root", "");
            return connect;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
