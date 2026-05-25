package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.datatypes.RESULT_STATUS;
import com.SmartLearningPlatform.Platform.entity.TestAttempt;
import com.SmartLearningPlatform.Platform.repository.TestAttemptRepository;
import com.SmartLearningPlatform.Platform.response.StudentMarksResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class StudentAnalyticsServiceImpl implements StudentAnalyticsService{

    private final TestAttemptRepository testAttemptRepository;

    public StudentAnalyticsServiceImpl(TestAttemptRepository testAttemptRepository) {
        this.testAttemptRepository = testAttemptRepository;
    }

    @Override
    public boolean isStudentPassInTest(Long userId, Long testAttemptId) throws Exception {

        List<TestAttempt> testAttempts = testAttemptRepository.findByUserId(userId);

        if(testAttempts.isEmpty())
        {
            throw new Exception("User don't give any test for now");
        }

        TestAttempt testAttempt = testAttempts.stream().filter(a -> a.getId() == testAttemptId)
                                   .findFirst().orElseThrow(() -> new RuntimeException("Not present"));

        return testAttempt.getResultStatus().equals(RESULT_STATUS.PASS);

    }

    @Override
    public StudentMarksResponse marksGetInTestByStudent(Long userId, Long testAttemptId) throws Exception {

        List<TestAttempt> testAttempts = testAttemptRepository.findByUserId(userId);

        TestAttempt testAttemptById = testAttempts.stream()
                 .filter(testAttempt -> Objects.equals(testAttempt.getId(), testAttemptId)).findFirst()
                 .orElseThrow(() -> new RuntimeException("TestAttempt not found with given id"));

        int total_Marks = testAttemptById.getQuestionPaper().getTotalMarks();
        double marks_score = testAttemptById.getScore();

        double percentage = (marks_score/total_Marks) * 100;

        StudentMarksResponse studentMarksResponse = new StudentMarksResponse();
        studentMarksResponse.setStudentMarks(marks_score);
        studentMarksResponse.setTotalMarks(total_Marks);
        studentMarksResponse.setPercentage(percentage);

        return studentMarksResponse;
    }
}
