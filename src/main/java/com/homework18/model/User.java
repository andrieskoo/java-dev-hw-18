package com.homework18.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@SoftDelete
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Size(max = 50)
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private Instant createdAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Instant deletedAt;

    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Note> createdNotes;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @PrePersist
    protected void setCreatedAtNow() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    @PreRemove
    public void preRemove() {
        this.deletedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                ", role=" + role +
                '}';
    }
}
