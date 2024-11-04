package org.example.controller;

import org.example.model.UserTask;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-tasks")
public class UserTaskController {

    private final UserTaskServiceImplementation userTaskService;

    @Autowired
    public UserTaskController(UserTaskServiceImplementation userTaskService) {
        this.userTaskService = userTaskService;
    }

    @PostMapping
    public ResponseEntity<UserTask> assignUserToTask(@RequestParam Integer taskId, @RequestParam Integer userId) {
        try {
            UserTask userTask = userTaskService.assignUserToTask(taskId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(userTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping
    public ResponseEntity<List<UserTask>> getAllUserTasks() {
        List<UserTask> userTasks = userTaskService.getAllUserTasks();
        if (userTasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // No user tasks found
        }
        return ResponseEntity.ok(userTasks); // Return the list of user tasks
    }


}
