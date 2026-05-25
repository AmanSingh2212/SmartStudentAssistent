package com.SmartLearningPlatform.Platform.service.geminiService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LlmEvaluationService {

    private final ChatClient geminiChatClient;

    public LlmEvaluationService(ChatClient.Builder geminiChatClient) {
        this.geminiChatClient = geminiChatClient.build();
    }

    public double evaluateSubjectiveMarks(
            String questionText,
            String expectedAnswer,
            String studentAnswer,
            int maxMarks) {

        String prompt = String.format("""
                You are an expert academic evaluator.

                Question:
                %s

                Expected Correct Answer:
                %s

                Student Answer:
                %s

                Maximum Marks:
                %d

                Rules:
                1. Compare semantically and conceptually.
                2. Allow partial marks.
                3. Ignore wording differences.
                4. For numerical answers:
                   - allow small rounding differences
                   - check mathematical correctness
                5. Never exceed max marks.

                VERY IMPORTANT:
                Return ONLY ONE NUMBER.
                No explanation.
                No markdown.
                No notes.

                Example outputs:
                0
                2.5
                %d
                """,
                questionText,
                expectedAnswer,
                studentAnswer,
                maxMarks,
                maxMarks
        );

        try {

            String result = geminiChatClient
                    .prompt(prompt)
                    .call()
                    .content()
                    .trim();

            System.out.println("Raw LLM Response = " + result);

            // Remove markdown
            result = result
                    .replace("```", "")
                    .replace("json", "")
                    .trim();

            // Extract LAST decimal/number from response
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)");
            Matcher matcher = pattern.matcher(result);

            double marks = -1;

            while (matcher.find()) {
                marks = Double.parseDouble(matcher.group());
            }

            if (marks == -1) {
                throw new RuntimeException(
                        "Could not extract marks from LLM response");
            }

            // Safety bounds
            if (marks < 0) {
                marks = 0;
            }

            if (marks > maxMarks) {
                marks = maxMarks;
            }

            System.out.println("Parsed Marks = " + marks);

            return marks;

        } catch (Exception e) {

            System.out.println(
                    "LLM evaluation failed: " + e.getMessage());

            // fallback strict comparison
            if (studentAnswer != null &&
                    studentAnswer.trim()
                            .equalsIgnoreCase(expectedAnswer.trim())) {

                return maxMarks;
            }

            return 0;
        }
    }
}