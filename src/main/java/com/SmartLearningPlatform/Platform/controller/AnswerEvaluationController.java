package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.TestAttempt;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.request.TestSubmissionRequest;
import com.SmartLearningPlatform.Platform.service.UserService;
import com.SmartLearningPlatform.Platform.service.geminiService.TestEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/answerEvaluation")
public class AnswerEvaluationController {

      private final TestEvaluationService testEvaluationService;
      private final UserService userService;

      public AnswerEvaluationController(TestEvaluationService testEvaluationService, UserService userService) {
        this.testEvaluationService = testEvaluationService;
          this.userService = userService;
      }


    @PostMapping("/submit/{questionPaperId}")
    public ResponseEntity<TestAttempt> submitTest(
            @PathVariable Long questionPaperId,
            @RequestBody TestSubmissionRequest request,
            @RequestHeader String Authorization
    ) throws Exception {
        User user = userService.findByJwtToken(Authorization);

        TestAttempt attempt = testEvaluationService.evaluateSubmission(
                questionPaperId,
                user,
                request.getStudentAnswers(),
                request.getStartTime()
        );

        return ResponseEntity.ok(attempt);
    }



}
