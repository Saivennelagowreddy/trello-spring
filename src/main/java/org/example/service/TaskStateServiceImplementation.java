package org.example.service;

import org.example.model.TaskState;
import org.example.repository.TaskStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskStateServiceImplementation {

    @Autowired
    private TaskStateRepository taskStateRepository;

    public List<TaskState> findAll() {
        return taskStateRepository.findAll();
    }

    public Optional<TaskState> findById(int id) {
        return taskStateRepository.findById(id);
    }

    public TaskState save(TaskState taskState) {
        return taskStateRepository.save(taskState);
    }

    public void deleteById(int id) {
        taskStateRepository.deleteById(id);
    }
}
