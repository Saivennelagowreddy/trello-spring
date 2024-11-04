package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.sql.Timestamp;
//import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private Timestamp createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskState> taskStates = new ArrayList<>(); // To track states over time

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Task() {}

    public Task(int id, String description, Timestamp createdAt) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
    }
    public TaskMemento saveToMemento() {
        return new TaskMemento(id, description, createdAt);
    }

    // Restores the state from a memento
    public void restoreFromMemento(TaskMemento memento) {
        this.id = memento.getId();
        this.description = memento.getDescription();
        this.createdAt = memento.getCreatedAt();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public Timestamp getCreatedAt() { return createdAt; }
//    public void setCreatedAt(Timestamp createdAt) { this.createdAt = timestamp; }

    public List<TaskState> getTaskStates() { return taskStates; }
    public void setTaskStates(List<TaskState> taskStates) { this.taskStates = taskStates; }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
