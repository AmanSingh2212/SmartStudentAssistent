package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Achievement;
import com.SmartLearningPlatform.Platform.request.UpdateAchievementRequest;

import java.util.List;

public interface AchievementService {

    Achievement addAchievement(UpdateAchievementRequest achievement) throws Exception;

    Achievement updateAchievement(Long achievementId, UpdateAchievementRequest updateAchievementRequest) throws Exception;

    List<Achievement> getAll() throws Exception;

    void removeAchievement(Long id) throws Exception;

}
