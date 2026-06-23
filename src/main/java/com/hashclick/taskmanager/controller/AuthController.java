package com.hashclick.taskmanager.controller;
import com.hashclick.taskmanager.dto.*;
import com.hashclick.taskmanager.model.User;
import com.hashclick.taskmanager.repository.UserRepository;
import com.hashclick.taskmanager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    private final UserRepository userRepo;
    public AuthController(AuthService service, UserRepository userRepo) { this.service = service; this.userRepo = userRepo; }

    @PostMapping("/register") public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest r) { return ResponseEntity.ok(service.register(r)); }
    @PostMapping("/login") public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest r) { return ResponseEntity.ok(service.login(r)); }

    @GetMapping("/make-admin")
    public ResponseEntity<String> makeAdmin(@RequestParam String email, @RequestParam String secret) {
        if (!"hashclick2026".equals(secret)) return ResponseEntity.status(403).body("Wrong secret");
        User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        u.setRole(User.Role.ADMIN);
        userRepo.save(u);
        return ResponseEntity.ok("Done! " + email + " is now ADMIN. Login again.");
    }
}
