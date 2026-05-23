package com.SmartLearningPlatform.Platform.repository;

import com.SmartLearningPlatform.Platform.entity.TestAttempt;
import com.SmartLearningPlatform.Platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {

     List<TestAttempt> findByUserId(Long userId);

}
