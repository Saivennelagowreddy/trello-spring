package org.example.model;

import java.sql.Timestamp;

public class TaskMemento {
    private final int id;
    private final String description;
    private final Timestamp createdAt;

    public TaskMemento(int id, String description, Timestamp createdAt) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public Timestamp getCreatedAt() { return createdAt; }
}
