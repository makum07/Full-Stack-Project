package com.skillup.taskmanager.controller;

import com.skillup.taskmanager.dto.DynamicRecordRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api/dynamic")
@CrossOrigin
public class DynamicCrudController {

    private final String baseUrl    = "jdbc:postgresql://localhost:5432/";
    private final String dbUser     = "postgres";
    private final String dbPassword = "Manohar@psql07";

    // -------------------------------
    // 1. Create record
    // -------------------------------
    @PostMapping("/create")
    public String insertRecord(@RequestBody DynamicRecordRequest req) {
        String url   = baseUrl + req.getDbName();
        String table = req.getTableName();
        Map<String, Object> data = req.getData();

        String cols        = String.join(", ", data.keySet());
        String placeholders= String.join(", ", Collections.nCopies(data.size(), "?"));
        String sql         = "INSERT INTO " + table + " (" + cols + ") VALUES (" + placeholders + ")";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int idx = 1;
            for (String col : data.keySet()) {
                Object val = data.get(col);
                // simple rule: if column name contains "date", cast to java.sql.Date
                if (val instanceof String && col.toLowerCase().contains("date")) {
                    stmt.setDate(idx++, java.sql.Date.valueOf((String) val));
                } else {
                    stmt.setObject(idx++, val);
                }
            }

            stmt.executeUpdate();
            return "Record inserted successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // -------------------------------
    // 2. Read all records
    // -------------------------------
    @PostMapping("/read")
    public List<Map<String, Object>> getRecords(@RequestBody DynamicRecordRequest req) {
        String url   = baseUrl + req.getDbName();
        String table = req.getTableName();
        List<Map<String, Object>> rows = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             Statement stmt   = conn.createStatement();
             ResultSet rs     = stmt.executeQuery("SELECT * FROM " + table)) {

            ResultSetMetaData m = rs.getMetaData();
            int colCount = m.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= colCount; i++) {
                    row.put(m.getColumnName(i), rs.getObject(i));
                }
                rows.add(row);
            }
            return rows;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // -------------------------------
    // 3. Update record by ID
    // -------------------------------
    @PutMapping("/update/{id}")
    public String updateRecord(
            @PathVariable Long id,
            @RequestBody DynamicRecordRequest req
    ) {
        String url   = baseUrl + req.getDbName();
        String table = req.getTableName();
        Map<String, Object> data = req.getData();

        String setClause = String.join(", ",
                data.keySet().stream().map(c -> c + " = ?").toList());
        String sql = "UPDATE " + table + " SET " + setClause + " WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int idx = 1;
            for (String col : data.keySet()) {
                Object val = data.get(col);
                if (val instanceof String && col.toLowerCase().contains("date")) {
                    stmt.setDate(idx++, java.sql.Date.valueOf((String) val));
                } else {
                    stmt.setObject(idx++, val);
                }
            }
            stmt.setLong(idx, id);
            stmt.executeUpdate();
            return "Record updated successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // -------------------------------
    // 4. Delete record by ID
    // -------------------------------
    @DeleteMapping("/delete/{id}")
    public String deleteRecord(
            @PathVariable Long id,
            @RequestBody DynamicRecordRequest req
    ) {
        String url   = baseUrl + req.getDbName();
        String table = req.getTableName();

        String sql = "DELETE FROM " + table + " WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
            return "Record deleted successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
