package com.SmartLearningPlatform.Platform.repository;

import com.SmartLearningPlatform.Platform.entity.User_Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAchievementRepository extends JpaRepository<User_Achievement, Long> {

    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);

    User_Achievement findByUserIdAndAchievementId(Long userId, Long achievementId);

}
