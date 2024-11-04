package org.example.controller;

import org.example.model.TaskState;
import org.example.service.TaskStateServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taskStates")
public class TaskStateController {

    @Autowired
    private TaskStateServiceImplementation taskStateService;

    @GetMapping
    public List<TaskState> getAllTaskStates() {
        return taskStateService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskState> getTaskStateById(@PathVariable int id) {
        Optional<TaskState> taskState = taskStateService.findById(id);
        return taskState.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskState> createTaskState(@RequestBody TaskState taskState) {
        TaskState savedTaskState = taskStateService.save(taskState);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskState(@PathVariable int id) {
        if (taskStateService.findById(id).isPresent()) {
            taskStateService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

