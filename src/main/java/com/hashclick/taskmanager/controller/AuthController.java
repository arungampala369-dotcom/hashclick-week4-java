package com.hashclick.taskmanager.controller;
import com.hashclick.taskmanager.dto.*;
import com.hashclick.taskmanager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/register") public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest r) { return ResponseEntity.ok(service.register(r)); }
    @PostMapping("/login") public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest r) { return ResponseEntity.ok(service.login(r)); }
}
