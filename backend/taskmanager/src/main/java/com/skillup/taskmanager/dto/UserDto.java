// 📁 dto/UserDto.java
package com.skillup.taskmanager.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
}