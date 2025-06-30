// 📁 dto/JwtAuthRequest.java
package com.skillup.taskmanager.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}