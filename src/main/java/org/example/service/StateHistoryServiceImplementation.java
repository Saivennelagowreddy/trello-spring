package org.example.service;


import org.example.model.StateHistory;
import org.example.model.Task;
import org.example.model.User;
import org.example.repository.StateHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class StateHistoryServiceImplementation {

    private final StateHistoryRepository stateHistoryRepository;

    @Autowired
    public StateHistoryServiceImplementation(StateHistoryRepository stateHistoryRepository) {
        this.stateHistoryRepository = stateHistoryRepository;
    }

    // Add entry to history when a user is assigned to a task
    public StateHistory addUserAssignedToTask(Task task, User user) {
        String changeDescription = "User " + user.getId() + " assigned to task " + task.getId();
        return createStateHistory(task, user, null, changeDescription);
    }

    // Add entry to history when a user is removed from a task
    public StateHistory addUserRemovedFromTask(Task task, User user) {
        String changeDescription = "User " + user.getId() + " removed from task " + task.getId();
        return createStateHistory(task, user, null, changeDescription);
    }

    // Add entry to history when a task is changed
    public StateHistory addTaskChanged(Task task, User user, String changeDescription) {
        return createStateHistory(task, user, null, changeDescription);
    }

    // Add entry to history when a comment is added to a task
    public StateHistory addCommentToTask(Task task, User user, Integer stateId, String commentDescription) {
        String changeDescription = "Comment added to task " + task.getId() + ": " + commentDescription;
        return createStateHistory(task, user, stateId, changeDescription);
    }

    // Helper method to create and save StateHistory entry
    private StateHistory createStateHistory(Task task, User user, Integer stateId, String changeDescription) {
        StateHistory stateHistory = new StateHistory(task, user, stateId, changeDescription);
        stateHistory.setChangedAt(new Timestamp(System.currentTimeMillis())); // Set timestamp
        return stateHistoryRepository.save(stateHistory);
    }
}
