package org.example.controller;

import org.example.model.Task;
import org.example.model.TaskStateEnum;
import org.example.service.TaskServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskServiceImplementation taskService;

    @Autowired
    public TaskController(TaskServiceImplementation taskService) {
        this.taskService = taskService;
    }

    // Endpoint to create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestParam String description) {
        Task createdTask = taskService.createTask(description);
        return ResponseEntity.status(201).body(createdTask);
    }

    // Endpoint to update the state of an existing task
    @PutMapping("/{taskId}/state")
    public ResponseEntity<Void> updateTaskState(@PathVariable int taskId, @RequestParam TaskStateEnum state) {
        boolean isUpdated = taskService.updateTaskState(taskId, state);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to restore a task to its previous state
    @PutMapping("/{taskId}/restore")
    public ResponseEntity<Void> restoreTaskState(@PathVariable int taskId) {
        boolean isRestored = taskService.restoreTaskState(taskId);
        if (isRestored) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // Endpoint to show all tasks
    @GetMapping
    public ResponseEntity<List<Task>> showBoard() {
        List<Task> tasks = taskService.showBoard();
        return ResponseEntity.ok(tasks);
    }
}
