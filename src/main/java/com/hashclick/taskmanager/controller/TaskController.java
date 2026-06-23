package com.hashclick.taskmanager.controller;
import com.hashclick.taskmanager.model.Task;
import com.hashclick.taskmanager.model.User;
import com.hashclick.taskmanager.service.TaskService;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Task>> getAll(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(service.getAll(u.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task, @AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(task, u.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task, @AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(service.update(id, task, u.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails u) {
        service.delete(id, u.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(service.getAllUsers(u.getUsername()));
    }
}
