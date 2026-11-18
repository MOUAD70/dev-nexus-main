package com.nexus.devnexus.enums.repository;

import com.nexus.devnexus.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByUuid(UUID uuid);
    Optional<Organization> findByName(String name);
    boolean existsByName(String name);
}
