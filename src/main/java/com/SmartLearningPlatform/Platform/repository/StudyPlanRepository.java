package com.SmartLearningPlatform.Platform.repository;


import com.SmartLearningPlatform.Platform.entity.StudyPlan;
import com.SmartLearningPlatform.Platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long> {

           @Query("select s.aiGeneratedPlan from StudyPlan s where s.user = :user")
           List<String>findAiGeneratedPlanByUser(User user);

}
