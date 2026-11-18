package com.nexus.devnexus.repository;

import com.nexus.devnexus.entity.Project;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByUuid(UUID uuid);

    Optional<Project> findByOrganizationIdAndProjectKey(Long organizationId, String projectKey);

    boolean existsByOrganizationIdAndProjectKey(Long organizationId, String projectKey);

    Page<Project> findByOrganizationId(Long organizationId, Pageable pageable);

    @Query("""
            select p from Project p
            where p.organization.id = :organizationId
              and exists (select 1 from ProjectMember pm
                          where pm.project = p and pm.user.id = :userId)
            """)
    Page<Project> findByOrganizationIdAndMemberUserId(@Param("organizationId") Long organizationId, @Param("userId") Long userId, Pageable pageable);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select p from Project p where p.id = :id")
//    Optional<Project> findByIdForUpdate(@Param("id") Long id);
}
