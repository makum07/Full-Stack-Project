package com.skillup.taskmanager.controller;

import com.skillup.taskmanager.dto.CreateTableRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api/database")
@CrossOrigin
public class DatabaseController {

    private final String baseUrl = "jdbc:postgresql://localhost:5432/";
    private final String dbUser = "postgres";
    private final String dbPassword = "Manohar@psql07";

    // 🔹 1. Create database - ADMIN only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createDatabase(@RequestParam String dbName) {
        try (Connection conn = DriverManager.getConnection(baseUrl + "postgres", dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE " + dbName);
            return "Database '" + dbName + "' created successfully.";

        } catch (Exception e) {
            if (e.getMessage().contains("already exists")) {
                return "Database already exists.";
            }
            return "Error: " + e.getMessage();
        }
    }

    // 🔹 2. Create table in a given database - ADMIN only
    @PostMapping("/table")
    @PreAuthorize("hasRole('ADMIN')")
    public String createTable(@RequestBody CreateTableRequest request) {
        String dbName = request.getDbName();
        String tableName = request.getTableName();
        List<CreateTableRequest.Field> fields = request.getFields();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            CreateTableRequest.Field field = fields.get(i);
            sb.append(field.getName()).append(" ").append(field.getType());
            if (i != fields.size() - 1) sb.append(", ");
        }

        String sql = "CREATE TABLE " + tableName + " (" + sb + ")";

        try (Connection conn = DriverManager.getConnection(baseUrl + dbName, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            return "Table '" + tableName + "' created successfully.";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
