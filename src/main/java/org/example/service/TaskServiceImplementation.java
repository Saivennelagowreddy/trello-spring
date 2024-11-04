package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.StateHistory;
import org.example.model.Task;
import org.example.model.TaskMemento;
import org.example.model.TaskState;
import org.example.model.TaskStateEnum;
import org.example.repository.StateHistoryRepository;
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
    private final StateHistoryRepository stateHistoryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository, TaskStateRepository taskStateRepository,
                                     StateHistoryRepository stateHistoryRepository) {
        this.taskRepository = taskRepository;
        this.taskStateRepository = taskStateRepository;
        this.stateHistoryRepository = stateHistoryRepository;
        this.objectMapper = new ObjectMapper();
    }

    public Task createTask(String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Set creation time

        // Save the initial task
        Task savedTask = taskRepository.save(task);

        // Save the initial state to the StateHistory
        saveTaskStateToHistory(savedTask);

        return savedTask;
    }

    public boolean updateTaskState(int taskId, TaskStateEnum state) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return false;
        }

        Task task = optionalTask.get();

        // Before making changes, save the current state
        saveTaskStateToHistory(task);

        // Update the task state
        TaskState taskState = new TaskState();
        taskState.setTask(task);
        taskState.setState(state);
        taskState.setTimestamp(new Timestamp(System.currentTimeMillis()));

        task.getTaskStates().add(taskState);
        taskRepository.save(task);

        return true;
    }

    public boolean restoreTaskState(int taskId) {
        // Exclude the current state and get the previous one
        List<StateHistory> historyList = stateHistoryRepository.findByTaskIdOrderByChangedAtDesc(taskId);

        if (historyList.size() < 2) {
            return false; // No previous state to restore
        }

        // Get the previous state (second in the list)
        StateHistory previousStateHistory = historyList.get(1);
        String taskStateSnapshot = previousStateHistory.getTaskStateSnapshot();

        try {
            // Deserialize the task state
            TaskMemento memento = objectMapper.readValue(taskStateSnapshot, TaskMemento.class);

            // Restore the task
            Task task = taskRepository.findById(taskId).orElse(null);
            if (task != null) {
                task.restoreFromMemento(memento);
                taskRepository.save(task);
                return true;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return false;
    }
    private Integer mapStateEnumToStateId(TaskStateEnum stateEnum) {
        // Assuming you have a mapping between TaskStateEnum and state IDs
        switch (stateEnum) {
            case TODO:
                return 1;
            case DOING:
                return 2;
            case DONE:
                return 3;
            default:
                throw new IllegalArgumentException("Unknown TaskStateEnum: " + stateEnum);
        }
    }

//    @Autowired
//    private StateRepository stateRepository;
//
//    private Integer mapStateEnumToStateId(TaskStateEnum stateEnum) {
//        State state = stateRepository.findByStateName(stateEnum.name());
//        if (state != null) {
//            return state.getId().intValue();
//        } else {
//            throw new IllegalStateException("State not found for TaskStateEnum: " + stateEnum);
//        }
//    }


    private Integer getCurrentStateId(Task task) {
        // Assuming Task has a method to get its current state
        List<TaskState> taskStates = task.getTaskStates();
        if (taskStates != null && !taskStates.isEmpty()) {
            // Get the most recent TaskState
            TaskState latestState = taskStates.get(taskStates.size() - 1);
            return mapStateEnumToStateId(latestState.getState());
        } else {
            // Handle the case where no state is set
            // For example, return a default stateId or throw an exception
            throw new IllegalStateException("Task has no states");
        }
    }


    private void saveTaskStateToHistory(Task task) {
        try {
            // Create a memento and serialize it to JSON
            TaskMemento memento = task.saveToMemento();
            String taskStateJson = objectMapper.writeValueAsString(memento);

            // Determine the current stateId
            Integer currentStateId = getCurrentStateId(task);

            // Save to StateHistory using the full constructor
            StateHistory stateHistory = new StateHistory(
                    task,
                    null, // User is null in this context
                    currentStateId,
                    "Task state saved",
                    taskStateJson,
                    new Timestamp(System.currentTimeMillis())
            );
            stateHistoryRepository.save(stateHistory);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public List<Task> showBoard() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTById(int taskId) {
        return taskRepository.findById(taskId);
    }
}
