package org.example.controller;

import org.example.model.Comment;
import org.example.model.Task;
import org.example.model.User;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImplementation commentService;
    @Autowired
    private TaskServiceImplementation taskService;

    @Autowired
    private UserServiceImplementation userService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(Math.toIntExact(id));
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Comment> createComment(@RequestBody Map<String, Object> payload,
                                                 @RequestParam Integer taskId,
                                                 @RequestParam Integer userId) {
        // Fetch Task and User objects by their IDs
//        System.out.println(taskId);
//        System.out.println(userId);
//        Optional<Task> task = taskService.findTById(taskId);
//        Optional<User> user = userService.getUserById(Long.valueOf(userId));
//
//        // Check if Task and User exist
//        if (task.isEmpty() || user.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        Comment comment = new Comment();
////        comment.setId(taskId);
        String text = (String) payload.get("text");
//        comment.setText(content);
//        // Set the fetched Task and User in the Comment
//        comment.setTask(task.get());
//        comment.setUser(user.get());
//
//        // Save the comment
//        Comment savedComment = commentService.save(comment, taskId, userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);

        // Fetch Task and User objects by their IDs
        Optional<Task> task = taskService.findTById(taskId);
        Optional<User> user = userService.getUserById(Long.valueOf(userId));
        System.out.println(task);
        // Check if Task and User exist
        if (task.isEmpty() || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Create and set up the Comment
        Comment comment = new Comment();
        comment.setText(text);
        comment.setTask(task.get());
        comment.setUser(user.get());

        // Save the comment
        Comment savedComment = commentService.save(comment, taskId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteById(Math.toIntExact(id));
        return ResponseEntity.noContent().build();
    }
}


