package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.TestAttempt;
import com.SmartLearningPlatform.Platform.repository.TestAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestAttemptServiceImpl implements TestAttemptService{

    private final TestAttemptRepository testAttemptRepository;

    public TestAttemptServiceImpl(TestAttemptRepository testAttemptRepository) {
        this.testAttemptRepository = testAttemptRepository;
    }

    @Override
    public TestAttempt getById(Long id) throws Exception {

        Optional<TestAttempt> testAttemptOptional = testAttemptRepository.findById(id);

        if(testAttemptOptional.isEmpty())
        {
            throw new Exception("No TestAttempt present with given id");
        }

        return testAttemptOptional.get();
    }

    @Override
    public List<TestAttempt> findByUserId(Long id) throws Exception {

        List<TestAttempt> testAttempts = testAttemptRepository.findByUserId(id);

        if(testAttempts.isEmpty())
        {
            throw new Exception("No single tests is given by student");
        }

        return testAttempts;
    }

    @Override
    public void deleteTestAttemptById(Long id) throws Exception {

        TestAttempt testAttempt = getById(id);

        testAttemptRepository.delete(testAttempt);

    }
}
