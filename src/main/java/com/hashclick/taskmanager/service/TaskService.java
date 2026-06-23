package com.hashclick.taskmanager.service;
import com.hashclick.taskmanager.model.*;
import com.hashclick.taskmanager.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo=taskRepo; this.userRepo=userRepo;
    }

    private User getUser(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Task> getAll(String email) {
        User u = getUser(email);
        return u.getRole() == User.Role.ADMIN ? taskRepo.findAll() : taskRepo.findByCreatedBy(u);
    }

    public Task getById(Long id) {
        return taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Task create(Task task, String email) {
        User u = getUser(email);
        task.setCreatedBy(u);
        task.setAssignedTo(u);
        return taskRepo.save(task);
    }

    public Task update(Long id, Task updated, String email) {
        Task t = getById(id);
        User u = getUser(email);
        if (u.getRole() != User.Role.ADMIN && !t.getCreatedBy().getEmail().equals(email))
            throw new RuntimeException("Access denied");
        t.setTitle(updated.getTitle());
        t.setDescription(updated.getDescription());
        t.setStatus(updated.getStatus());
        t.setPriority(updated.getPriority());
        t.setDueDate(updated.getDueDate());
        return taskRepo.save(t);
    }

    public void delete(Long id, String email) {
        Task t = getById(id);
        User u = getUser(email);
        if (u.getRole() != User.Role.ADMIN && !t.getCreatedBy().getEmail().equals(email))
            throw new RuntimeException("Access denied");
        taskRepo.deleteById(id);
    }

    public List<User> getAllUsers(String email) {
        User u = getUser(email);
        if (u.getRole() != User.Role.ADMIN) throw new RuntimeException("Access denied");
        return userRepo.findAll();
    }
}
