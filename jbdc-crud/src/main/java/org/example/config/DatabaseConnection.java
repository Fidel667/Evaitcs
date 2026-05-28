package org.example.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //singleton pattern
    private static DatabaseConnection instance;
    // connection
    private final Connection connection;
    //private constructor
    private DatabaseConnection() {
        try {

            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Subhub",
                    "root",
                    "301140"
            );
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to connect to database", exception);
        }
    }
    //method to get instance
    public static DatabaseConnection getInstance() {

        if (instance == null) {
            instance = new DatabaseConnection();


        }
        return instance;
    }
    //method to get connection

    public Connection getConnection() {
        return connection;
    }

}
