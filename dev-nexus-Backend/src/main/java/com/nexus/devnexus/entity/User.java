package com.nexus.devnexus.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password_hash;
    @Column(nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private boolean enabled;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    public String getFullMame(String first_name, String last_name){
        return first_name + " " + last_name;
    }

    public void updateProfile(String first_name, String last_name){
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void changePassword(String newPasswordHash){
        this.password_hash = newPasswordHash;
    }

    public void disable(){
        this.enabled = false;
    }

    public void enable(){
        this.enabled = true;
    }

}
