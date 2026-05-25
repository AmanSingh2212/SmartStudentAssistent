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
    private final AnswerRepository answersRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final LlmEvaluationService llmEvaluationService;

    public TestEvaluationService(
            QuestionPaperRepository questionPaperRepository,
            AnswerRepository answersRepository,
            TestAttemptRepository testAttemptRepository,
            LlmEvaluationService llmEvaluationService) {

        this.questionPaperRepository = questionPaperRepository;
        this.answersRepository = answersRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.llmEvaluationService = llmEvaluationService;
    }

    @Transactional
    public TestAttempt evaluateSubmission(
            Long questionPaperId,
            User user,
            List<String> studentAnswers,
            LocalDateTime startTime) {

        // 1. Fetch Question Paper
        QuestionPaper paper = questionPaperRepository.findById(questionPaperId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Question Paper not found"));

        // 2. Fetch Answer Key
        Answers correctAnswersKey =
                answersRepository.findByQuestionPaperId(questionPaperId)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Answer Key not generated for this paper"));

        List<Question> questions = paper.getQuestions();
        List<String> correctAnswersList = correctAnswersKey.getAnswers();

        int totalQuestions = questions.size();

        int correctCount = 0;
        int wrongCount = 0;

        // IMPORTANT → now supports partial marks
        double accumulatedScore = 0;

        // 3. Evaluate Answers
        for (int i = 0; i < totalQuestions; i++) {

            Question question = questions.get(i);

            String studentAns =
                    (i < studentAnswers.size())
                            ? studentAnswers.get(i)
                            : "";

            String correctAns =
                    (i < correctAnswersList.size())
                            ? correctAnswersList.get(i)
                            : "";

            double obtainedMarks = 0;

            // Blank answer
            if (studentAns == null ||
                    studentAns.trim().isEmpty()) {

                wrongCount++;

                System.out.println(
                        "Q" + (i + 1) +
                                " blank → 0 marks");

                continue;
            }

            // MCQ evaluation
            if (question.getQuestionType() == QuestionType.MCQ) {

                if (studentAns.trim()
                        .equalsIgnoreCase(correctAns.trim())) {

                    obtainedMarks = question.getMarks();
                    correctCount++;
                } else {
                    wrongCount++;
                }

            } else {

                // NUMERICAL / LONG_ANSWER / SUBJECTIVE
                obtainedMarks =
                        llmEvaluationService.evaluateSubjectiveMarks(
                                question.getQuestionText(),
                                correctAns,
                                studentAns,
                                question.getMarks()
                        );

                if (obtainedMarks > 0) {
                    correctCount++;
                } else {
                    wrongCount++;
                }
            }

            accumulatedScore += obtainedMarks;

            // Debug logs
            System.out.println("--------------------");
            System.out.println("Question " + (i + 1));
            System.out.println("Student: " + studentAns);
            System.out.println("Correct: " + correctAns);
            System.out.println("Marks Awarded: " + obtainedMarks);
        }

        // 4. Metrics
        LocalDateTime endTime = LocalDateTime.now();

        int secondsTaken =
                (int) Duration.between(startTime, endTime)
                        .toSeconds();

        double accuracyPercentage =
                totalQuestions > 0
                        ? ((double) correctCount / totalQuestions) * 100
                        : 0;

        // Pass = >=40%
        RESULT_STATUS status =
                accumulatedScore >= (paper.getTotalMarks() * 0.4)
                        ? RESULT_STATUS.PASS
                        : RESULT_STATUS.FAIL;

        // 5. Save Attempt
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