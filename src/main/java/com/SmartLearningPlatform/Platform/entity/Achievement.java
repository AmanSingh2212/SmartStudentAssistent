package com.SmartLearningPlatform.Platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "badge_image", length = 500)
    private String badgeImage;

    // Bidirectional Link to Bridge Table
    @Builder.Default
    @OneToMany(mappedBy = "achievement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User_Achievement> userAchievements = new ArrayList<>();

}
