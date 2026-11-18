package com.nexus.devnexus.repository;

import com.nexus.devnexus.entity.OrganizationMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {
    @EntityGraph(attributePaths = {"user", "role"})
    Page<OrganizationMember> findByOrganizationId(Long organizationId, Pageable pageable);

    @EntityGraph(attributePaths = {"organization", "role"})
    List<OrganizationMember> findByUserId(Long userId);
    Optional<OrganizationMember> findByOrganizationIdAndUserId(Long organizationId, Long userId);
    boolean existsByOrganizationIdAndUserId(Long organizationId, Long userId);
    void deleteByOrganizationIdAndUserId(Long organizationId, Long userId);
}
