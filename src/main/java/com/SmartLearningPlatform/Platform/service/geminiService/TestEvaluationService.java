package com.SmartLearningPlatform.Platform.service.geminiService;

import com.SmartLearningPlatform.Platform.datatypes.QuestionType;
import com.SmartLearningPlatform.Platform.datatypes.RESULT_STATUS;
import com.SmartLearningPlatform.Platform.entity.*;
import com.SmartLearningPlatform.Platform.repository.AnswerRepository;
import com.SmartLearningPlatform.Platform.repository.QuestionPaperRepository;
import com.SmartLearningPlatform.Platform.repository.TestAttemptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestEvaluationService {

    private final QuestionPaperRepository questionPaperRepository;
    private final AnswerRepository answersRepository; // Your correct answer key repository
    private final TestAttemptRepository testAttemptRepository;
    private final LlmEvaluationService llmEvaluationService; // For checking descriptive items

    public TestEvaluationService(QuestionPaperRepository questionPaperRepository,
                                 AnswerRepository answersRepository,
                                 TestAttemptRepository testAttemptRepository,
                                 LlmEvaluationService llmEvaluationService) {
        this.questionPaperRepository = questionPaperRepository;
        this.answersRepository = answersRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.llmEvaluationService = llmEvaluationService;
    }

    @Transactional
    public TestAttempt evaluateSubmission(Long questionPaperId, User user, List<String> studentAnswers,
                                              LocalDateTime startTime) {
        // 1. Fetch Question Paper and the correct Answer Key
        QuestionPaper paper = questionPaperRepository.findById(questionPaperId)
                .orElseThrow(() -> new IllegalArgumentException("Question Paper not found"));

        Answers correctAnswersKey = answersRepository.findByQuestionPaperId(questionPaperId)
                .orElseThrow(() -> new IllegalStateException("Answer Key not generated for this paper yet"));

        List<Question> questions = paper.getQuestions();
        List<String> correctAnswersList = correctAnswersKey.getAnswers();

        int totalQuestions = questions.size();
        int correctCount = 0;
        int wrongCount = 0;
        int accumulatedScore = 0;

        // 2. Loop and compare student input against correct answers
        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);

            // Handle cases where a student left an answer blank or skipped it
            String studentAns = (i < studentAnswers.size()) ? studentAnswers.get(i) : "";
            String correctAns = (i < correctAnswersList.size()) ? correctAnswersList.get(i) : "";

            boolean isCorrect = false;

            if (studentAns == null || studentAns.trim().isEmpty()) {
                wrongCount++;
                continue;
            }

            if (question.getQuestionType() == QuestionType.MCQ) {
                // Direct case-insensitive string matching for MCQs
                isCorrect = studentAns.trim().equalsIgnoreCase(correctAns.trim());
            } else {
                // For LONG_ANSWER, NUMERICAL, or complex items, let the LLM check semantic equivalence
                isCorrect = llmEvaluationService.evaluateSubjectiveAnswer(
                        question.getQuestionText(),
                        correctAns,
                        studentAns
                );
            }

            if (isCorrect) {
                correctCount++;
                accumulatedScore += question.getMarks();
            } else {
                wrongCount++;
            }
        }

        // 3. Compute Metrics
        LocalDateTime endTime = LocalDateTime.now();
        int secondsTaken = (int) Duration.between(startTime, endTime).toSeconds();

        double accuracyPercentage = totalQuestions > 0
                ? ((double) correctCount / totalQuestions) * 100.0
                : 0.0;

        // Arbitrary pass condition: passing score is greater than or equal to 40% of total marks
        RESULT_STATUS status = (accumulatedScore >= (paper.getTotalMarks() * 0.4))
                ? RESULT_STATUS.PASS
                : RESULT_STATUS.FAIL;

        // 4. Map into Entity and Persist
        TestAttempt attempt = TestAttempt.builder()
                .user(user)
                .questionPaper(paper)
                .answers(studentAnswers)
                .startTime(startTime)
                .endTime(endTime)
                .score(accumulatedScore)
                .correctAnswers(correctCount)
                .wrongAnswers(wrongCount)
                .accuracy(accuracyPercentage)
                .timeTaken(secondsTaken)
                .resultStatus(status)
                .build();

        return testAttemptRepository.save(attempt);
    }
}