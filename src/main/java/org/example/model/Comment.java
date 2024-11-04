package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

// Assuming this class is annotated for JPA/Hibernate
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("text")
    private String text;

//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
//
//    @ManyToOne // Assuming many comments can belong to one task
//    @JoinColumn(name = "task_id") // Name of the foreign key column
//    private Task task;

    @ManyToOne // Assuming many comments can belong to one user
    @JoinColumn(name = "user_id") // Name of the foreign key column
    private User user;

    public Comment() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user; // This is the method that was missing
    }
}
