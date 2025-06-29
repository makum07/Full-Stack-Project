package com.skillup.taskmanager.dto;

import lombok.Data;
import java.util.Map;

@Data
public class DynamicRecordRequest {
    private String dbName;
    private String tableName;
    private Map<String, Object> data;
}
