package org.example.repository;

import org.example.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
    // Additional query methods can be defined here if needed
}
