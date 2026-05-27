package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.Achievement;
import com.SmartLearningPlatform.Platform.request.UpdateAchievementRequest;
import com.SmartLearningPlatform.Platform.service.AchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PostMapping
    public ResponseEntity<?> addAchievement(@RequestBody UpdateAchievementRequest request) {
        try {
            Achievement achievement = achievementService.addAchievement(request);
            return new ResponseEntity<>(achievement, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{achievementId}")
    public ResponseEntity<?> updateAchievement(
            @PathVariable Long achievementId,
            @RequestBody UpdateAchievementRequest request) {
        try {
            Achievement achievement = achievementService.updateAchievement(achievementId, request);
            return new ResponseEntity<>(achievement, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAchievements() {
        try {
            List<Achievement> achievements = achievementService.getAll();
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{achievementId}")
    public ResponseEntity<?> deleteAchievement(@PathVariable Long achievementId) {
        try {
            achievementService.removeAchievement(achievementId);
            return new ResponseEntity<>("Achievement deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
