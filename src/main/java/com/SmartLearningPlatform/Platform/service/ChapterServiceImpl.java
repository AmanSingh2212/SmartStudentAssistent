package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Chapter;
import com.SmartLearningPlatform.Platform.entity.Subject;
import com.SmartLearningPlatform.Platform.repository.ChapterRepository;
import com.SmartLearningPlatform.Platform.repository.SubjectRepository;
import com.SmartLearningPlatform.Platform.request.CreateChapterRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService{

    private final ChapterRepository chapterRepository;
    private final SubjectRepository subjectRepository;

    public ChapterServiceImpl(ChapterRepository chapterRepository, SubjectRepository subjectRepository) {
        this.chapterRepository = chapterRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Chapter createChapter(CreateChapterRequest createChapterRequest, String subjectName, String subjectStandard)
            throws Exception {

        // 1. Fetch the parent subject using your existing JPA method
        Subject subject = subjectRepository.findByNameAndStandard(subjectName, subjectStandard);

        // 2. Fail-fast if the subject isn't found in the database
        if (subject == null) {
            throw new Exception("Subject not found with name: " + subjectName + " and standard: " + subjectStandard);
        }

        // 3. Instantiate and populate the new Chapter entity
        Chapter chapter = new Chapter();
        chapter.setName(createChapterRequest.getName());
        chapter.setChapterIndex(createChapterRequest.getChapterIndex());
        chapter.setDescription(createChapterRequest.getDescription());
//        chapter.setEstimatedStudyTime(createChapterRequest.getEstimatedStudyTime());

        // 4. Link the chapter to its parent subject (Assuming a @ManyToOne relationship in Chapter)
        chapter.setSubject(subject);

        // 5. Save and return the newly created chapter
        return chapterRepository.save(chapter);

    }

    @Override
    public List<Chapter> getChaptersBySubject(String subjectName, String subjectStandard) throws Exception {

        // 1. Fetch the parent subject using your existing JPA method
        Subject subject = subjectRepository.findByNameAndStandard(subjectName, subjectStandard);

        // 2. Fail-fast if the subject isn't found in the database
        if (subject == null) {
            throw new Exception("Subject not found with name: " + subjectName + " and standard: " + subjectStandard);
        }

        return chapterRepository.findBySubject(subject);

    }

    @Override
    public Chapter updateChapter(Long id, CreateChapterRequest createChapterRequest) throws Exception {

        // 1. Fetch the existing chapter by ID
        Chapter existingChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new Exception("Chapter not found with id: " + id));

        // 2. Only update fields if they are explicitly provided in the request
        if (createChapterRequest.getName() != null) {
            existingChapter.setName(createChapterRequest.getName());
        }

        if (createChapterRequest.getChapterIndex() != null) {
            existingChapter.setChapterIndex(createChapterRequest.getChapterIndex());
        }

        if (createChapterRequest.getDescription() != null) {
            existingChapter.setDescription(createChapterRequest.getDescription());
        }

        // 3. Save and return the updated object
        return chapterRepository.save(existingChapter);

    }

    @Override
    public void deleteChapter(Long id) throws Exception {

        Chapter existingChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new Exception("Chapter not found with id: " + id));

        chapterRepository.delete(existingChapter);

    }
}
