package com.nexus.devnexus.enums.repository;

import com.nexus.devnexus.entity.Role;
import com.nexus.devnexus.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
