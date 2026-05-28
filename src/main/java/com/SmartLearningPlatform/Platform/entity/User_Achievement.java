package com.SmartLearningPlatform.Platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User_Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("userAchievements")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    @JsonIgnoreProperties("userAchievements")
    private Achievement achievement;

    @Column(name = "earned_at", nullable = false)
    private LocalDateTime earnedAt;

    // Optional helper hook to automatically set the timestamp before saving to MySQL
    @PrePersist
    protected void onCreate() {
        this.earnedAt = LocalDateTime.now();
    }
}
