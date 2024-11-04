package org.example.service;

import org.example.model.Comment;
import org.example.model.Task;
import org.example.model.User;
import org.example.repository.CommentRepository;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository; // Add @Autowired here

    @Autowired
    private UserRepository userRepository; // Add @Autowired here

    // Retrieve all comments
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    // Find a comment by its ID
    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    // Save a new comment with associated task and user
    public Comment save(Comment comment, Integer taskId, Integer userId) {
        if (taskId != null) {
            Task task = taskRepository.findById(taskId).orElse(null);
            comment.setTask(task); // Associate the comment with a task if provided
        }
        if (userId != null) {
            User user = userRepository.findById(Math.toIntExact(Long.valueOf(userId))).orElse(null);
            comment.setUser(user); // Associate the comment with a user if provided
        }
        return commentRepository.save(comment);
    }

    // Delete a comment by its ID
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
