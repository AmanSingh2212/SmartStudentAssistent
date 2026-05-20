package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Chapter;
import com.SmartLearningPlatform.Platform.request.CreateChapterRequest;

import java.util.List;

public interface ChapterService {

    Chapter createChapter(CreateChapterRequest createChapterRequest,
                              String subjectName, String subjectStandard) throws Exception;

    List<Chapter> getChaptersBySubject(String subjectName, String subjectStandard) throws Exception;

   Chapter updateChapter(Long id, CreateChapterRequest createChapterRequest) throws Exception;

    void deleteChapter(Long id) throws Exception;

}
