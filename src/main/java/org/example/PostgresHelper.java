package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresHelper {

    public void readFromDb(){
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "root");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Objects;");

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            rs.close();
            st.close();
            conn.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
