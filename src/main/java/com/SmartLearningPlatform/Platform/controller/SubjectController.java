package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.Subject;
import com.SmartLearningPlatform.Platform.request.UpdateSubjectRequest;
import com.SmartLearningPlatform.Platform.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 1. Create a new Subject
    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        try {
            Subject createdSubject = subjectService.createSubject(subject);
            return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
        } catch (Exception e) {
            // Catches validation failures (e.g., "Subject name cannot be null or empty")
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. Get Subjects by Standard
    @GetMapping("/standard/{standard}")
    public ResponseEntity<?> getSubjectsByStandard(@PathVariable String standard) {
        try {
            List<Subject> subjects = subjectService.getSubjectsByStandard(standard);
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (Exception e) {
            // Catches empty list failures (e.g., "Subjects are not present with given standard")
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 3. Update an existing Subject
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(
            @PathVariable Long id,
            @RequestBody UpdateSubjectRequest updateSubjectRequest) {
        try {
            Subject updatedSubject = subjectService.updateSubject(updateSubjectRequest, id);
            return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
        } catch (Exception e) {
            // Catches lookup failures (e.g., "Subject not found with id: X")
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 4. Delete a Subject
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return new ResponseEntity<>("Subject deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
