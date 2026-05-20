package com.SmartLearningPlatform.Platform.controller;

import com.SmartLearningPlatform.Platform.entity.Chapter;
import com.SmartLearningPlatform.Platform.request.CreateChapterRequest;
import com.SmartLearningPlatform.Platform.service.ChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    // 1. Create chapter under a subject
    @PostMapping
    public ResponseEntity<Chapter> createChapter(
            @RequestBody CreateChapterRequest request,
            @RequestParam String subjectName,
            @RequestParam String subjectStandard) {
        try {
            Chapter created = chapterService.createChapter(request, subjectName, subjectStandard);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 2. Get all chapters of a subject
    @GetMapping
    public ResponseEntity<List<Chapter>> getChaptersBySubject(
            @RequestParam String subjectName,
            @RequestParam String subjectStandard) {
        try {
            List<Chapter> chapters = chapterService.getChaptersBySubject(subjectName, subjectStandard);
            return new ResponseEntity<>(chapters, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. Update a chapter by id (partial update using CreateChapterRequest fields)
    @PutMapping("/{id}")
    public ResponseEntity<Chapter> updateChapter(
            @PathVariable Long id,
            @RequestBody CreateChapterRequest request) {
        try {
            Chapter updated = chapterService.updateChapter(id, request);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 4. Delete a chapter by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        try {
            chapterService.deleteChapter(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
