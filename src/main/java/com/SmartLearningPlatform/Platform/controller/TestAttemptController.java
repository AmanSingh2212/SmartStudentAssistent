package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.TestAttempt;
import com.SmartLearningPlatform.Platform.service.TestAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testAttempt")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;

    public TestAttemptController(TestAttemptService testAttemptService) {
        this.testAttemptService = testAttemptService;
    }

    @GetMapping("/get")
    private ResponseEntity<TestAttempt> getById(@RequestParam Long testAttemptId) throws Exception {

        TestAttempt testAttempt = testAttemptService.getById(testAttemptId);

        return new ResponseEntity<>(testAttempt, HttpStatus.OK);

    }

    @GetMapping("/getByUser")
    private ResponseEntity<List<TestAttempt>> getByUserId(@RequestParam Long userId) throws Exception {

        List<TestAttempt> testAttempts = testAttemptService.findByUserId(userId);

        return new ResponseEntity<>(testAttempts, HttpStatus.OK);

    }

    @DeleteMapping("/deleteById")
    private ResponseEntity<String> deleteById(@RequestParam Long id) throws Exception {

        testAttemptService.deleteTestAttemptById(id);

        return new ResponseEntity<>("Test Attempt deleted successfully", HttpStatus.OK);

    }

}
