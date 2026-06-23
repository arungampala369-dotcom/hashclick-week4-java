package com.hashclick.taskmanager.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING) private Status status = Status.TODO;
    @Enumerated(EnumType.STRING) private Priority priority = Priority.MEDIUM;
    private LocalDate dueDate;
    @ManyToOne @JoinColumn(name = "created_by") private User createdBy;
    @ManyToOne @JoinColumn(name = "assigned_to") private User assignedTo;

    public enum Status { TODO, IN_PROGRESS, DONE }
    public enum Priority { LOW, MEDIUM, HIGH }

    public Long getId() { return id; }
    public String getTitle() { return title; } public void setTitle(String t) { title = t; }
    public String getDescription() { return description; } public void setDescription(String d) { description = d; }
    public Status getStatus() { return status; } public void setStatus(Status s) { status = s; }
    public Priority getPriority() { return priority; } public void setPriority(Priority p) { priority = p; }
    public LocalDate getDueDate() { return dueDate; } public void setDueDate(LocalDate d) { dueDate = d; }
    public User getCreatedBy() { return createdBy; } public void setCreatedBy(User u) { createdBy = u; }
    public User getAssignedTo() { return assignedTo; } public void setAssignedTo(User u) { assignedTo = u; }
}
