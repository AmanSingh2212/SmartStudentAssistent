package com.SmartLearningPlatform.Platform.service.geminiService;

import com.SmartLearningPlatform.Platform.entity.StudyPlan;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.repository.StudyPlanRepository;
import com.SmartLearningPlatform.Platform.request.CreateStudyPlanRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiStudyPlanService {

    private final StudyPlanRepository studyPlanRepository;
    private final ChatClient chatClient;

    public AiStudyPlanService(StudyPlanRepository studyPlanRepository, ChatClient.Builder chatClient) {
        this.studyPlanRepository = studyPlanRepository;
        this.chatClient = chatClient.build();
    }

    public StudyPlan createStudyPlan(CreateStudyPlanRequest studyPlan, User user) {

        String prompt = """
                You are an expert educational mentor and study planner.

                Create a detailed, practical and motivating study plan.

                Student Details:
                Target Exam: %s
                Daily Study Hours: %d
                Start Date: %s
                End Date: %s
                Goal: %s

                Instructions:
                1. Divide preparation into phases.
                2. Create daily and weekly study strategy.
                3. Include revision schedule.
                4. Include mock tests and practice strategy.
                5. Keep the plan realistic according to daily hours.
                6. Return well formatted plain text only.

                Generate the study plan now.
                """
                .formatted(
                        studyPlan.getTargetExam(),
                        studyPlan.getDailyHours(),
                        studyPlan.getStartDate(),
                        studyPlan.getEndDate(),
                        studyPlan.getGoal()
                );

        String aiPlan = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        StudyPlan studyPlan1 = new StudyPlan();
        studyPlan1.setGoal(studyPlan.getGoal());
        studyPlan1.setDailyHours(studyPlan.getDailyHours());
        studyPlan1.setStartDate(studyPlan.getStartDate());
        studyPlan1.setEndDate(studyPlan.getEndDate());
        studyPlan1.setTargetExam(studyPlan.getTargetExam());
        studyPlan1.setAiGeneratedPlan(aiPlan);
        studyPlan1.setUser(user);

        return studyPlanRepository.save(studyPlan1);
    }
}