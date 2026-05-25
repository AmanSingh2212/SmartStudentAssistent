package com.SmartLearningPlatform.Platform.controller;


import com.SmartLearningPlatform.Platform.response.StudentMarksResponse;
import com.SmartLearningPlatform.Platform.service.StudentAnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class StudentAnalyticsController {

     private final StudentAnalyticsService studentAnalyticsService;

    public StudentAnalyticsController(StudentAnalyticsService studentAnalyticsService) {
        this.studentAnalyticsService = studentAnalyticsService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> checkStudentPassStatus(
            @RequestParam Long userId,
            @RequestParam Long testAttemptId) {
        try {
            boolean isPassed = studentAnalyticsService.isStudentPassInTest(userId, testAttemptId);
            return ResponseEntity.ok(isPassed);
        } catch (RuntimeException e) {
            // Catches the "Not present" error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Catches the "User don't give any test for now" error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/student")
    public ResponseEntity<?> studentAnalysis(
            @RequestParam Long userId,
            @RequestParam Long testAttemptId
            ){
        try {
            StudentMarksResponse studentMarksResponse = studentAnalyticsService.marksGetInTestByStudent(userId, testAttemptId);
            return ResponseEntity.ok(studentMarksResponse);
        } catch (RuntimeException e) {
            // Catches the "Not present" error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Catches the "User don't give any test for now" error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


