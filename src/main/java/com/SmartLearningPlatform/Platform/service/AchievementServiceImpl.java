package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Achievement;
import com.SmartLearningPlatform.Platform.repository.AchievementRepository;
import com.SmartLearningPlatform.Platform.request.UpdateAchievementRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService{

    private final AchievementRepository achievementRepository;

    public AchievementServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Achievement addAchievement(UpdateAchievementRequest achievement) throws Exception {


        if (achievement == null) {
            throw new Exception("Achievement request cannot be null");
        }

        if (achievement.getTitle() == null || achievement.getTitle().isBlank()) {
            throw new Exception("Title is required");
        }

        if (achievement.getDescription() == null || achievement.getDescription().isBlank()) {
            throw new Exception("Description is required");
        }

        if (achievement.getPoints() == null || achievement.getPoints() < 0) {
            throw new Exception("Points must be greater than or equal to 0");
        }

        Achievement newAchievement = new Achievement();
        newAchievement.setTitle(achievement.getTitle().trim());
        newAchievement.setDescription(achievement.getDescription().trim());
        newAchievement.setPoints(achievement.getPoints());
        newAchievement.setBadgeImage(
                achievement.getBadgeImage() != null ? achievement.getBadgeImage().trim() : null
        );

        return achievementRepository.save(newAchievement);

    }

    @Override
    public Achievement updateAchievement(Long achievementId, UpdateAchievementRequest updateAchievementRequest)
            throws Exception {

        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new Exception("Achievement not found with id: " + achievementId));

        if (updateAchievementRequest == null) {
            throw new Exception("Update request cannot be null");
        }

        if (updateAchievementRequest.getTitle() == null || updateAchievementRequest.getTitle().isBlank()) {
            throw new Exception("Title is required");
        }

        if (updateAchievementRequest.getDescription() == null || updateAchievementRequest.getDescription().isBlank()) {
            throw new Exception("Description is required");
        }

        if (updateAchievementRequest.getPoints() == null || updateAchievementRequest.getPoints() < 0) {
            throw new Exception("Points must be greater than or equal to 0");
        }

        achievement.setTitle(updateAchievementRequest.getTitle().trim());
        achievement.setDescription(updateAchievementRequest.getDescription().trim());
        achievement.setPoints(updateAchievementRequest.getPoints());
        achievement.setBadgeImage(
                updateAchievementRequest.getBadgeImage() != null
                        ? updateAchievementRequest.getBadgeImage().trim()
                        : null
        );

        return achievementRepository.save(achievement);

    }

    @Override
    public List<Achievement> getAll() throws Exception {
        return achievementRepository.findAll();
    }

    @Override
    public void removeAchievement(Long id) throws Exception {

       Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with given id"));

       achievementRepository.delete(achievement);

    }
}
