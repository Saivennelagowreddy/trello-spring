package org.example.repository;

import org.example.model.StateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateHistoryRepository extends JpaRepository<StateHistory, Long> {
    // Additional query methods can be defined here if needed
}
