package org.example.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "state_history")
public class StateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "state_id", nullable = false)
    private Integer stateId; // ID of the new state after transition

    @Column(name = "change_description")
    private String changeDescription; // Description of the change (e.g., "Moved to Todo")

    @Column(name = "changed_at", nullable = false, updatable = false)
    private Timestamp changedAt;

    @Lob
    @Column(name = "task_state_snapshot")
    private String taskStateSnapshot;

    // Constructor with required fields
    public StateHistory(Task task, User user, Integer stateId, String changeDescription) {
        this.task = task;
        this.user = user;
        this.stateId = stateId;
        this.changeDescription = changeDescription;
        this.changedAt = new Timestamp(System.currentTimeMillis());
    }
    public StateHistory(Task task, User user, Integer stateId, String changeDescription, String taskStateSnapshot, Timestamp changedAt) {
        this.task = task;
        this.user = user;
        this.stateId = stateId;
        this.changeDescription = changeDescription;
        this.taskStateSnapshot = taskStateSnapshot;
        this.changedAt = changedAt;
    }


    // Getter for changedAt
    public Timestamp getChangedAt() {
        return changedAt;
    }
    public String getTaskStateSnapshot() {
        return taskStateSnapshot;
    }

    public void setTaskStateSnapshot(String taskStateSnapshot) {
        this.taskStateSnapshot = taskStateSnapshot;
    }

    // Setter for changedAt
    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
    }
}
