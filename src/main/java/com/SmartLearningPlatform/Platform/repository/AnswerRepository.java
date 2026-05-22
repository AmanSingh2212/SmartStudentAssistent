package com.SmartLearningPlatform.Platform.repository;

import com.SmartLearningPlatform.Platform.entity.Answers;
import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answers, Long> {

    Optional<Answers> findByQuestionPaperId(Long questionPaperId);

}
