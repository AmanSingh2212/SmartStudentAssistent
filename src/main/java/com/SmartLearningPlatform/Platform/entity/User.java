package com.SmartLearningPlatform.Platform.entity;

import com.SmartLearningPlatform.Platform.datatypes.BOARD;
import com.SmartLearningPlatform.Platform.datatypes.ROLE;
import com.SmartLearningPlatform.Platform.datatypes.TARGETEXAM;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 15)
    private String mobile;

    private String standard; // e.g., "10th", "11th", "12th"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BOARD board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ROLE role;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private TARGETEXAM targetExam;

    @Builder.Default
    private Integer totalPoints = 0;

    @Builder.Default
    private Integer streakDays = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
