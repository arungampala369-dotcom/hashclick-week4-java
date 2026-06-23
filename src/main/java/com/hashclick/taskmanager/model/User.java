package com.hashclick.taskmanager.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity @Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    @Column(unique = true) private String email;
    @JsonIgnore private String password;
    @Enumerated(EnumType.STRING) private Role role = Role.USER;
    public enum Role { USER, ADMIN }

    public Long getId() { return id; }
    public String getName() { return name; } public void setName(String n) { name = n; }
    public String getEmail() { return email; } public void setEmail(String e) { email = e; }
    public String getPassword() { return password; } public void setPassword(String p) { password = p; }
    public Role getRole() { return role; } public void setRole(Role r) { role = r; }
}
