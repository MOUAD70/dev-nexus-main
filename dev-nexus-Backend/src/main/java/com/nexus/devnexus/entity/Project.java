package com.nexus.devnexus.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String projectKey;

    @Column(nullable = false)
    private String name;

    private String description;

    private int nextTaskNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addMember(User user, Role role) {
        ProjectMember member = new ProjectMember();
        member.setProject(this);
        member.setUser(user);
        member.setRole(role);
        member.setJoined_at(LocalDateTime.now());

        this.members.add(member);
    }

    public void removeMember(User user) {
        this.members.removeIf(m -> m.getUser().equals(user));
    }

    public boolean isMember(User user) {
        return this.members.stream().anyMatch(m -> m.getUser().equals(user));
    }

    public synchronized int allocateTaskNumber() {
        return ++this.nextTaskNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<ProjectMember> members = new ArrayList<>();
}
