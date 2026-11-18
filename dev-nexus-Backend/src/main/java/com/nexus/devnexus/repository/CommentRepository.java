package com.nexus.devnexus.repository;

import com.nexus.devnexus.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"author", "task"})
    Optional<Comment> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = "author")
    Page<Comment> findByTaskId(Long taskId, Pageable pageable);

    long countByTaskId(Long taskId);
}
