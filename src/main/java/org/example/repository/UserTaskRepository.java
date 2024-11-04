package org.example.repository;

import org.example.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {
    // Additional query methods can be defined here if needed
}
