package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.entity.Sub;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubRepository {
    private final Connection connection;

    public SubRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Database connection is null");
        }
    }

    public Sub save(Sub sub) {

        String sql = "INSERT INTO subhub(sub_name, sub_type, cost) VALUES(?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sub.getSubName());
            ps.setString(2, sub.getSubType());
            ps.setBigDecimal(3, BigDecimal.valueOf(sub.getCost()));

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("NEW SUB ADDED");

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    sub.setSubId(rs.getLong(1));
                }
            }

            return sub;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}