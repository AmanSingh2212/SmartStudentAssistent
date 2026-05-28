package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.User_Achievement;

public interface UserAchievementService {

    User_Achievement awardAchievementToUser(Long userId, Long achievementId) throws Exception;

    void removeAchievementToUser(Long userId, Long achievementId) throws Exception;

}
