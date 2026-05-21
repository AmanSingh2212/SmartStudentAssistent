package com.SmartLearningPlatform.Platform.repository;

import com.SmartLearningPlatform.Platform.entity.Chapter;
import com.SmartLearningPlatform.Platform.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

      List<Chapter> findBySubject(Subject subject);

      Optional<Chapter> findByNameAndSubjectId(String name, Long id);

}
