package com.SmartLearningPlatform.Platform.service.geminiService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LlmEvaluationService {

    private final ChatClient geminiChatClient; // Inject your LLM client instance

    public LlmEvaluationService(ChatClient.Builder geminiChatClient) {
        this.geminiChatClient = geminiChatClient.build();
    }

    public boolean evaluateSubjectiveAnswer(String questionText, String expectedAnswer, String studentAnswer) {
        String prompt = String.format(
                "You are an academic grader evaluating a student's answer.\n\n" +
                        "Question Given: %s\n" +
                        "Expected Correct Solution/Points: %s\n" +
                        "Student's Submission: %s\n\n" +
                        "Task: Compare the student's submission against the expected solution. " +
                        "If the core facts, concepts, numerical values, or mathematical assertions match semantically " +
                        "(even if phrased slightly differently, formatted with or without markup, or missing non-essential words), mark it as true.\n" +
                        "If the answer is completely factually incorrect, conceptually broken, or missing, mark it as false.\n\n" +
                        "Respond ONLY with the single word: 'TRUE' or 'FALSE'. Do not provide any notes, greetings, or explanations.",
                questionText, expectedAnswer, studentAnswer
        );

        try {
            String result = geminiChatClient.prompt(prompt).toString().trim();
            return result.equalsIgnoreCase("TRUE");
        } catch (Exception e) {
            // Fallback strategy: if LLM call crashes, default safely to a strict string comparison
            return studentAnswer.trim().equalsIgnoreCase(expectedAnswer.trim());
        }
    }
}
