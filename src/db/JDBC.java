package db;

import java.sql.*;
import patientDemo.MainPageGUI;

public class JDBC extends MainPageGUI{

    private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/patient_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // connection to the database
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            System.out.println("Veri bağlantısı yapıldı.");
            
    

            con.close();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}
