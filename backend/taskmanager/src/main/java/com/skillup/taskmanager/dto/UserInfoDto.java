package com.skillup.taskmanager.dto;

public class UserInfoDto {
    private String username;
    private String role;

    // Constructor
    public UserInfoDto() {}

    public UserInfoDto(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString(), equals(), hashCode() if needed
    @Override
    public String toString() {
        return "UserInfoDto{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}