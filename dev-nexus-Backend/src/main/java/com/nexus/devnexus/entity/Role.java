package com.nexus.devnexus.entity;

import com.nexus.devnexus.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    private String description;

    public boolean isAtLeast(RoleName other) {

        return this.name.ordinal() <= other.ordinal();
    }
}
