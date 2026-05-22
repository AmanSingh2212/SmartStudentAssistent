package com.SmartLearningPlatform.Platform.service.geminiService;

import com.SmartLearningPlatform.Platform.dto.AnswerResponse;
import com.SmartLearningPlatform.Platform.entity.Answers;
import com.SmartLearningPlatform.Platform.entity.Question;
import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import com.SmartLearningPlatform.Platform.repository.AnswerRepository;
import com.SmartLearningPlatform.Platform.repository.QuestionPaperRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiAnswerGenerationService {

    private final ChatClient chatClient;
    private final QuestionPaperRepository questionPaperRepository;
    private final AnswerRepository answersRepository;

    public AiAnswerGenerationService(
            ChatClient.Builder chatClient,
            QuestionPaperRepository questionPaperRepository,
            AnswerRepository answersRepository
    ) {
        this.chatClient = chatClient.build();
        this.questionPaperRepository = questionPaperRepository;
        this.answersRepository = answersRepository;
    }

    public Answers generateAnswers(Long paperId) {

        QuestionPaper paper = questionPaperRepository.findById(paperId)
                .orElseThrow(() -> new RuntimeException("Paper not found"));

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an expert teacher.

                Generate correct answers for the following questions.

                Return ONLY valid JSON.

                Format:
                {
                  "answers":[
                    "answer1",
                    "answer2"
                  ]
                }

                Questions:
                """);

        int count = 1;

        for (Question q : paper.getQuestions()) {

            prompt.append("\n")
                    .append(count++)
                    .append(". ")
                    .append(q.getQuestionText());

            if (q.getOptions() != null && !q.getOptions().isEmpty()) {

                prompt.append("\nOptions:\n");

                for (String op : q.getOptions()) {
                    prompt.append("- ")
                            .append(op)
                            .append("\n");
                }
            }

            prompt.append("\n");
        }

        // Spring AI auto-converts response
        AnswerResponse aiResponse = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .entity(AnswerResponse.class);

        Answers answers = new Answers();

        answers.setQuestionPaper(paper);
        assert aiResponse != null;
        answers.setAnswers(aiResponse.getAnswers());

        return answersRepository.save(answers);
    }
}