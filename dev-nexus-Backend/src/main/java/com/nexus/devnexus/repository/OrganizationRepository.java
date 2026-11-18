package com.nexus.devnexus.repository;

import com.nexus.devnexus.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByUuid(UUID uuid);
    
    @Query("select o from Organization o where lower(o.name) = lower(:name)")
    Optional<Organization> findByName(@Param("name") String name);

    @Query("select count(o) > 0 from Organization o where lower(o.name) = lower(:name)")
    boolean existsByName(@Param("name") String name);

    @Query("select om.organization from OrganizationMember om where om.user.id = :userId")
    List<Organization> findByMemberUserId(@Param("userId") Long userId);
}
