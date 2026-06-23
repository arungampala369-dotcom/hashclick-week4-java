package com.hashclick.taskmanager.dto;
public class RegisterRequest {
    private String name, email, password;
    public String getName() { return name; } public void setName(String n) { name = n; }
    public String getEmail() { return email; } public void setEmail(String e) { email = e; }
    public String getPassword() { return password; } public void setPassword(String p) { password = p; }
}
