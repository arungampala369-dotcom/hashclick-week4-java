package com.hashclick.taskmanager.repository;
import com.hashclick.taskmanager.model.Task;
import com.hashclick.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedBy(User user);
}
