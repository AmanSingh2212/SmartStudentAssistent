package com.SmartLearningPlatform.Platform.controller;


import com.SmartLearningPlatform.Platform.entity.User_Achievement;
import com.SmartLearningPlatform.Platform.service.UserAchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserAchievementController {

    private final UserAchievementService userAchievementService;

    public UserAchievementController(UserAchievementService userAchievementService) {
        this.userAchievementService = userAchievementService;
    }


    @PostMapping("/{userId}/achievements/{achievementId}")
    public ResponseEntity<?> awardAchievement(
            @PathVariable Long userId,
            @PathVariable Long achievementId) {

        try {
            User_Achievement userAchievement = userAchievementService.awardAchievementToUser(userId, achievementId);

            return ResponseEntity.status(HttpStatus.CREATED).body(userAchievement);

        } catch (Exception e) {
            // Catches missing users or achievements (404 Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{userId}/achievements/{achievementId}")
    public ResponseEntity<?> deleteAchievement(
            @PathVariable Long userId,
            @PathVariable Long achievementId) {

        try {
            userAchievementService.removeAchievementToUser(userId, achievementId);

            return ResponseEntity.status(HttpStatus.CREATED).body("User Achievement deleted successfully");

        } catch (Exception e) {
            // Catches missing users or achievements (404 Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }

}

