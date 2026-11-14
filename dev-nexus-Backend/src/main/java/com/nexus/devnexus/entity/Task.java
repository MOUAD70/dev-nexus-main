package com.nexus.devnexus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexus.devnexus.enums.TaskPriority;
import com.nexus.devnexus.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false, updatable = false)
    private int task_number;

    @Column(nullable = false)
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TO_DO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority = TaskPriority.MEDIUM;


    private LocalDate due_date;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;


    public String getKey() {
        return project.getProjectKey() + "-" + task_number;
    }

    public void assignTo(User user) {
        this.assignee = user;
    }

    public void unassign() {
        this.assignee = null;
    }

    public void changeStatus(TaskStatus next) {
        this.status = next;
        this.updated_at = LocalDateTime.now();
    }

    public boolean isOverdue() {
        if (this.status == TaskStatus.DONE || this.due_date == null) {
            return false;
        }
        return LocalDate.now().isAfter(this.due_date);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

}
