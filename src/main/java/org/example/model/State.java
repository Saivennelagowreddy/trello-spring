package org.example.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_name", nullable = false) // This ensures that the field is not null
    private String stateName; // Use camelCase for naming conventions

    // Getters and setters...
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
