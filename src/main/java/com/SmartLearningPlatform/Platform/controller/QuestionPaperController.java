package com.SmartLearningPlatform.Platform.controller;


import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import com.SmartLearningPlatform.Platform.service.AnswerService;
import com.SmartLearningPlatform.Platform.service.QuestionPaperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/QuestionPaper")
public class QuestionPaperController {

      private final QuestionPaperService questionPaperService;

      public QuestionPaperController(QuestionPaperService questionPaperService) {
        this.questionPaperService = questionPaperService;
      }


      @GetMapping("/get/{id}")
      private ResponseEntity<QuestionPaper> getQuestionPaper(@PathVariable Long id) throws Exception {

          QuestionPaper questionPaper = questionPaperService.findById(id);

          return new ResponseEntity<>(questionPaper, HttpStatus.OK);

      }

}
