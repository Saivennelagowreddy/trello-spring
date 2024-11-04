package org.example.controller;

import org.example.model.Task;
import org.example.model.TaskStateEnum;
import org.example.service.TaskServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
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

    // Endpoint to show all tasks
    @GetMapping
    public ResponseEntity<List<Task>> showBoard() {
        List<Task> tasks = taskService.showBoard();
        return ResponseEntity.ok(tasks);
    }
}
