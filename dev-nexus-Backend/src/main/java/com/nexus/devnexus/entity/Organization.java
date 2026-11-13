package com.nexus.devnexus.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;


    @OneToMany(mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<OrganizationMember> members = new ArrayList<>();

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addMember(User user, Role role) {
        OrganizationMember member = new OrganizationMember();
        member.setOrganization(this);
        member.setUser(user);
        member.setRole(role);
        member.setJoined_at(LocalDateTime.now());

        this.members.add(member);
    }

    public void removeMember(User user) {
        this.members.removeIf(m -> m.getUser().equals(user));
    }

    public OrganizationMember findMember(User user) {
        return this.members.stream()
                .filter(m -> m.getUser().equals(user))
                .findFirst()
                .orElse(null);
    }

    public int getMemberCount() {
        return this.members.size();
    }
}



