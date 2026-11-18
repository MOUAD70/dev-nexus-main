package com.nexus.devnexus.repository;

import com.nexus.devnexus.entity.ProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    @EntityGraph(attributePaths = "user")
    Page<ProjectMember> findByProjectId(Long projectId, Pageable pageable);

    @EntityGraph(attributePaths = "project")
    List<ProjectMember> findByUserId(Long userId);

    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
    long countByProjectId(Long projectId);
    void deleteByProjectIdAndUserId(Long projectId, Long userId);
}
