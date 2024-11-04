package org.example.repository;

import org.example.model.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskState, Integer> {
    // Additional query methods can be defined here if needed
}
