package com.skillup.taskmanager.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateTableRequest {
    private String dbName;
    private String tableName;
    private List<Field> fields;

    @Data
    public static class Field {
        private String name;
        private String type;
    }
}