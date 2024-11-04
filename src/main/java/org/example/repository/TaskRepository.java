package org.example.repository;

import org.example.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    // Additional query methods can be defined here if needed
}
