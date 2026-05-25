package com.SmartLearningPlatform.Platform.service;


import com.SmartLearningPlatform.Platform.entity.TestAttempt;

import java.util.List;

public interface TestAttemptService {

      TestAttempt getById(Long id) throws Exception;

      List<TestAttempt> findByUserId(Long id) throws Exception;

      void deleteTestAttemptById(Long id) throws Exception;

}
