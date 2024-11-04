package org.example.service;

import org.example.model.Task;
import org.example.model.User;
import org.example.model.UserTask;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserTaskServiceImplementation {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserTaskServiceImplementation(UserTaskRepository userTaskRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.userTaskRepository = userTaskRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Method to assign a user to a task
    public UserTask assignUserToTask(Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserTask userTask = new UserTask();
        userTask.setTask(task);
        userTask.setUser(user);
        userTask.setAssignedAt(new Timestamp(System.currentTimeMillis()));

        return userTaskRepository.save(userTask);
    }

    // New method to get all UserTask assignments
    public List<UserTask> getAllUserTasks() {
        return userTaskRepository.findAll(); // Fetch all user-task assignments from the repository
    }
}
