package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.StudyPlan;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.repository.StudyPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyPlanServiceImpl implements StudyPlanService{

    private final StudyPlanRepository studyPlanRepository;

    public StudyPlanServiceImpl(StudyPlanRepository studyPlanRepository) {
        this.studyPlanRepository = studyPlanRepository;
    }

    @Override
    public String getAiPlanByUser(User user) throws Exception {

        List<String> studyPlans = studyPlanRepository.findAiGeneratedPlanByUser(user);

        if(studyPlans.isEmpty())
        {
            throw new Exception("No study plan present for user");
        }

        String data = studyPlans.getFirst();

        return formatStudyPlan(data);

    }

    public String formatStudyPlan(String rawText) {
        return rawText
                .replace("\\n", "\n")
                .replace("\\\"", "\"")
                .replaceAll("\n{3,}", "\n\n")
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1")
                .replaceAll("(?m)^\\*\\s+", "• ")
                .strip();
    }


}
