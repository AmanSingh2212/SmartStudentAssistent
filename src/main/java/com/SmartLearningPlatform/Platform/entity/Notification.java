package com.SmartLearningPlatform.Platform.entity;

import com.SmartLearningPlatform.Platform.datatypes.NOTIFICATION_TYPE;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Link back to the targeted User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("notifications") // Prevents infinite JSON serialization recursion
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "is_read", nullable = false) // 'read' is a reserved keyword in some SQL dialects, 'is_read' is safer
    private boolean read = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NOTIFICATION_TYPE type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}