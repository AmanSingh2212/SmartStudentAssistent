package com.SmartLearningPlatform.Platform.request;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAchievementRequest {

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "badge_image", length = 500)
    private String badgeImage;

}
