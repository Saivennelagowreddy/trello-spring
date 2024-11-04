package org.example.service;

import org.example.model.TaskMemento;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TaskCaretaker {
    // Map to hold stacks of mementos for each task
    private Map<Integer, Stack<TaskMemento>> mementoMap = new HashMap<>();

    // Adds a memento for a task
    public void addMemento(int taskId, TaskMemento memento) {
        mementoMap.computeIfAbsent(taskId, k -> new Stack<>()).push(memento);
    }

    // Gets the last memento for a task
    public TaskMemento getLastMemento(int taskId) {
        Stack<TaskMemento> stack = mementoMap.get(taskId);
        if (stack != null && !stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }
}
