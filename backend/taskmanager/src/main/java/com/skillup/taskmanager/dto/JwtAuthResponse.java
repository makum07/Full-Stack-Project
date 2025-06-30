// 📁 dto/JwtAuthResponse.java
package com.skillup.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
}