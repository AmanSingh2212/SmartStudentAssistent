package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import com.SmartLearningPlatform.Platform.request.GenerateQuestionPaperRequest;
import com.SmartLearningPlatform.Platform.service.geminiService.AiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {

    private final AiService aiService;

    public GeminiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate-question-paper")
    public ResponseEntity<QuestionPaper> generateQuestionPaper(
            @Valid @RequestBody GenerateQuestionPaperRequest request) {

        QuestionPaper paper = aiService.generateQuestionPaper(
                request.getSubjectId(),
                request.getChapterNames(),
                request.getNoOfQuestions(),
                request.getTotalMarks(),
                request.getDuration(),
                request.getTitle(),
                request.getInstructions(),
                request.getCreatedBy(),
                request.getPaperType()
        );

        return ResponseEntity.ok(paper);
    }

}
