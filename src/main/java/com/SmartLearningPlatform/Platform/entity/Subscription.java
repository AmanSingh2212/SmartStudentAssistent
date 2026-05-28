package com.SmartLearningPlatform.Platform.entity;


import com.SmartLearningPlatform.Platform.datatypes.PLAN;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Linking to your existing User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("subscriptions") // Prevents infinite JSON serialization loops
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PLAN plan;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate; // Can be null if it's a lifetime or open-ended free tier

    @Column(nullable = false)
    private boolean active;

    @PrePersist
    protected void onCreate() {
        if (this.startDate == null) {
            this.startDate = LocalDateTime.now();
        }
    }
}