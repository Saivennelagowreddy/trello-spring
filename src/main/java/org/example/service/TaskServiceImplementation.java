package org.example.service;

import org.example.model.Task;
import org.example.model.TaskState;
import org.example.model.TaskStateEnum;
import org.example.repository.TaskRepository;
import org.example.repository.TaskStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImplementation {

    private final TaskRepository taskRepository;
    private final TaskStateRepository taskStateRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository, TaskStateRepository taskStateRepository) {
        this.taskRepository = taskRepository;
        this.taskStateRepository = taskStateRepository;
    }

    public Task createTask(String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Set creation time

        // Save the initial task without a user assignment
        return taskRepository.save(task);
    }

    public boolean updateTaskState(int taskId, TaskStateEnum state) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return false;
        }

        TaskState taskState = new TaskState();
        taskState.setTask(task);
        taskState.setState(state);
        taskState.setTimestamp(new Timestamp(System.currentTimeMillis()));

        task.getTaskStates().add(taskState);
        taskRepository.save(task);

        return true;
    }

    public List<Task> showBoard() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTById(int taskId) {
        return taskRepository.findById(taskId);
    }
}