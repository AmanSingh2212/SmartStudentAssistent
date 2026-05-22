package com.SmartLearningPlatform.Platform.controller;


import com.SmartLearningPlatform.Platform.entity.Answers;
import com.SmartLearningPlatform.Platform.service.QuestionPaperService;
import com.SmartLearningPlatform.Platform.service.geminiService.AiAnswerGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class GenerateAnswerController {

     private final AiAnswerGenerationService aiAnswerGenerationService;

    public GenerateAnswerController(AiAnswerGenerationService aiAnswerGenerationService) {
        this.aiAnswerGenerationService = aiAnswerGenerationService;
    }

    @PostMapping("/generate/{paperId}")
    public ResponseEntity<Answers> generateAnswers(
            @PathVariable Long paperId
    ) {

        Answers generatedAnswers =
                aiAnswerGenerationService.generateAnswers(paperId);

        return ResponseEntity.ok(generatedAnswers);
    }

}
