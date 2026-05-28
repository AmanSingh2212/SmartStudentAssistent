package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Achievement;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.entity.User_Achievement;
import com.SmartLearningPlatform.Platform.repository.AchievementRepository;
import com.SmartLearningPlatform.Platform.repository.UserAchievementRepository;
import com.SmartLearningPlatform.Platform.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAchievementServiceImpl implements UserAchievementService{

    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

    public UserAchievementServiceImpl(UserAchievementRepository userAchievementRepository, UserRepository userRepository, AchievementRepository achievementRepository) {
        this.userAchievementRepository = userAchievementRepository;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
    }

    @Override
    public User_Achievement awardAchievementToUser(Long userId, Long achievementId) throws Exception {

        // 1. Check if the user has already unlocked this achievement
        if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)) {
            throw new IllegalStateException("User has already earned this achievement.");
        }

        // 2. Fetch the User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));

        // 3. Fetch the Achievement
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new Exception("Achievement not found with id: " + achievementId));

        // 4. Create the new bridge entity record
        User_Achievement userAchievement = new User_Achievement();
        userAchievement.setUser(user);
        userAchievement.setAchievement(achievement);
        // Note: 'earnedAt' is handled automatically by your @PrePersist hook!

        // 5. Update Gamification Metrics: Add achievement points to user's profile
        int updatedPoints = (user.getTotalPoints() != null ? user.getTotalPoints() : 0) + achievement.getPoints();
        user.setTotalPoints(updatedPoints);

        // 6. Establish the bidirectional sync & save
        user.getUserAchievements().add(userAchievement);

        // Saving the user cascades the persist action to the user_achievements table automatically
        userRepository.save(user);

        return userAchievement;

    }

    @Override
    public void removeAchievementToUser(Long userId, Long achievementId) throws Exception {

        User_Achievement userAchievement = userAchievementRepository
                                           .findByUserIdAndAchievementId(userId, achievementId);

        if(userAchievement == null)
        {
            throw new Exception("UserAchievement not found");
        }

        userAchievementRepository.delete(userAchievement);

    }
}
