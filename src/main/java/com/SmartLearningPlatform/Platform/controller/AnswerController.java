package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.Answers;
import com.SmartLearningPlatform.Platform.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @GetMapping("/all")
    private ResponseEntity<List<List<String>>> getAll() throws Exception {

        List<List<String>> answers = answerService.getAll();

        return new ResponseEntity<>(answers, HttpStatus.OK);

    }

    @GetMapping("/get")
    private ResponseEntity<List<String>> getById(@RequestParam Long id) throws Exception {

        List<String> answer = answerService.getAnswerById(id);

        return new ResponseEntity<>(answer, HttpStatus.OK);

    }

    @GetMapping("/get/questionPaper")
    private ResponseEntity<List<String>> getByQpId(@RequestParam Long id) throws Exception {

        List<String> answer = answerService.getAnswersByQuestionPaperId(id);

        return new ResponseEntity<>(answer, HttpStatus.OK);

    }

}
