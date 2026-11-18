package com.nexus.devnexus.repository;

import com.nexus.devnexus.entity.Task;
import com.nexus.devnexus.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @EntityGraph(attributePaths = {"project", "assignee", "reporter"})
    Optional<Task> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"project", "assignee", "reporter"})
    Optional<Task> findByProjectIdAndTaskNumber(Long projectId, int taskNumber);

    @EntityGraph(attributePaths = "assignee")
    Page<Task> findByProjectId(Long projectId, Pageable pageable);

    @EntityGraph(attributePaths = "project")
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);

    long countByProjectId(Long projectId);

    long countByProjectIdAndStatus(Long projectId, TaskStatus status);

    @EntityGraph(attributePaths = "assignee")
    Page<Task> findByProjectIdAndDueDateBeforeAndStatusNot(Long projectId, LocalDate date, TaskStatus status, Pageable pageable);
}
