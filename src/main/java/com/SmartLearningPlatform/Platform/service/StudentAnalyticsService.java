package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.response.StudentMarksResponse;

public interface StudentAnalyticsService {

     boolean isStudentPassInTest(Long userId, Long testAttemptId) throws Exception;

     StudentMarksResponse marksGetInTestByStudent(Long userId) throws Exception;

}
