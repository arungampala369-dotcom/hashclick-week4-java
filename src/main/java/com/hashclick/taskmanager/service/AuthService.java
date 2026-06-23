package com.hashclick.taskmanager.service;
import com.hashclick.taskmanager.dto.*;
import com.hashclick.taskmanager.model.User;
import com.hashclick.taskmanager.repository.UserRepository;
import com.hashclick.taskmanager.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwt, AuthenticationManager authManager) {
        this.repo=repo; this.encoder=encoder; this.jwt=jwt; this.authManager=authManager;
    }

    public AuthResponse register(RegisterRequest r) {
        if (repo.existsByEmail(r.getEmail())) throw new IllegalArgumentException("Email already exists");
        User u = new User();
        u.setName(r.getName()); u.setEmail(r.getEmail()); u.setPassword(encoder.encode(r.getPassword()));
        repo.save(u);
        return new AuthResponse(jwt.generateToken(u.getEmail()), u.getName(), u.getEmail(), u.getRole().name());
    }

    public AuthResponse login(AuthRequest r) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(r.getEmail(), r.getPassword()));
        User u = repo.findByEmail(r.getEmail()).orElseThrow();
        return new AuthResponse(jwt.generateToken(u.getEmail()), u.getName(), u.getEmail(), u.getRole().name());
    }
}
