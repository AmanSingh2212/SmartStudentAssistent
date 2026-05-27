package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.StudyPlan;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.request.CreateStudyPlanRequest;
import com.SmartLearningPlatform.Platform.service.StudyPlanService;
import com.SmartLearningPlatform.Platform.service.UserService;
import com.SmartLearningPlatform.Platform.service.geminiService.AiStudyPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studyPlan")
public class StudyPlanController {

    private final AiStudyPlanService aistudyPlanService;
    private final StudyPlanService studyPlanService;
    private final UserService userService;

    public StudyPlanController(AiStudyPlanService aistudyPlanService, StudyPlanService studyPlanService, UserService userService) {
        this.aistudyPlanService = aistudyPlanService;
        this.studyPlanService = studyPlanService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<StudyPlan> createPlan(
            @RequestBody CreateStudyPlanRequest studyPlan,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findByJwtToken(jwt);

        return ResponseEntity.ok(
                aistudyPlanService.createStudyPlan(studyPlan, user)
        );
    }

    @GetMapping("/getFirstByUser")
    public ResponseEntity<String> getStudyPlanByUser(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findByJwtToken(jwt);
        return ResponseEntity.ok(
              studyPlanService.getAiPlanByUser(user)
        );
    }

}
