package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.User;

import java.util.List;

public interface StudyPlanService {

    String getAiPlanByUser(User user) throws Exception;

    public String formatStudyPlan(String rawText);

}
