package com.SmartLearningPlatform.Platform.service.geminiService;

import com.SmartLearningPlatform.Platform.datatypes.Difficulty;
import com.SmartLearningPlatform.Platform.datatypes.PaperType;
import com.SmartLearningPlatform.Platform.datatypes.QuestionType;
import com.SmartLearningPlatform.Platform.dto.AiQuestionItem;
import com.SmartLearningPlatform.Platform.dto.AiQuestionPaperResponse;
import com.SmartLearningPlatform.Platform.entity.Chapter;
import com.SmartLearningPlatform.Platform.entity.Question;
import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import com.SmartLearningPlatform.Platform.entity.Subject;
import com.SmartLearningPlatform.Platform.repository.ChapterRepository;
import com.SmartLearningPlatform.Platform.repository.QuestionPaperRepository;
import com.SmartLearningPlatform.Platform.repository.SubjectRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AiService {

    private final ChatClient chatClient;
    private final SubjectRepository subjectRepository;
    private final ChapterRepository chapterRepository;
    private final QuestionPaperRepository questionPaperRepository;

    public AiService(ChatClient.Builder chatClientBuilder,
                     SubjectRepository subjectRepository,
                     ChapterRepository chapterRepository,
                     QuestionPaperRepository questionPaperRepository) {
        this.chatClient = chatClientBuilder
                .build();
        this.subjectRepository = subjectRepository;
        this.chapterRepository = chapterRepository;
        this.questionPaperRepository = questionPaperRepository;
    }

    public QuestionPaper generateQuestionPaper(Long subjectId,
                                               List<String> chapterNames,
                                               Integer noOfQuestions,
                                               Integer totalMarks,
                                               Integer duration,
                                               String title,
                                               String instructions,
                                               String createdBy,
                                               PaperType paperType) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));

        String chapters = String.join(", ", chapterNames);

        String prompt = """
Generate a CBSE question paper in strict JSON format.

Rules:
- Use all chapters listed.
- Generate exactly %d questions.
- Total marks must be exactly %d.
- First 3 questions should be 1-mark MCQs.
- Remaining questions should mostly be 3-mark and 5-mark questions.
- Difficulty should be moderate to hard.
- Include numericals only where chapter naturally supports numericals.
- Do not include markdown.
- Return only valid JSON.

Input:
{
  "title": "%s",
  "standard": "%s",
  "subject": "%s",
  "chapters": "%s",
  "noOfQuestions": %d,
  "totalMarks": %d,
  "duration": %d,
  "paperType": "%s",
  "instructions": "%s"
}

For questionType, use ONLY one of these exact values:
MCQ, SHORT_ANSWER, LONG_ANSWER, NUMERICAL

For difficulty, use ONLY one of these exact values:
EASY, MEDIUM, HARD

Expected JSON fields:
- title
- totalMarks
- duration
- instructions
- paperType
- questions: array of objects

                IMPORTANT JSON RULES:
                - Return ONLY valid JSON.
                - Do not wrap JSON in markdown or triple backticks.
                - Escape all newlines inside string values using \\\\n.
                - Do not put raw line breaks inside any JSON string field.
                - Use only double quotes for JSON keys and string values.
                - questionText, correctAnswer, and explanation must be valid JSON strings.
                - If a question has multiple lines, represent them with \\\\n inside the string.

Each question object must contain:
- questionText
- questionType
- difficulty
- chapterName
- correctAnswer
- explanation
- marks
""".formatted(
                noOfQuestions,
                totalMarks,
                title,
                subject.getStandard(),
                subject.getName(),
                chapters,
                noOfQuestions,
                totalMarks,
                duration,
                paperType.name(),
                instructions == null ? "" : instructions
        );

        AiQuestionPaperResponse aiResponse = chatClient.prompt()
                .system("You are an expert CBSE paper setter. Return only valid JSON.")
                .user(prompt)
                .call()
                .entity(AiQuestionPaperResponse.class);

        QuestionPaper paper = new QuestionPaper();
        paper.setTitle(aiResponse.getTitle() != null ? aiResponse.getTitle() : title);
        paper.setSubject(subject);
        paper.setQuestions(new ArrayList<>());
        paper.setTotalMarks(aiResponse.getTotalMarks() != null ? aiResponse.getTotalMarks() : totalMarks);
        paper.setDuration(aiResponse.getDuration() != null ? aiResponse.getDuration() : duration);
        paper.setGeneratedByAI(true);
        paper.setCreatedBy(createdBy);
        paper.setCreatedAt(LocalDateTime.now());
        paper.setPaperType(aiResponse.getPaperType() != null
                ? PaperType.valueOf(aiResponse.getPaperType().toUpperCase())
                : paperType);
        paper.setInstructions(aiResponse.getInstructions() != null ? aiResponse.getInstructions() : instructions);

        List<Question> savedQuestions = new ArrayList<>();

        if (aiResponse.getQuestions() != null) {
            for (AiQuestionItem item : aiResponse.getQuestions()) {

                Chapter chapter = chapterRepository
                        .findByNameAndSubjectId(item.getChapterName(), subject.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Chapter not found: " + item.getChapterName()));

                Question question = new Question();
                question.setQuestionText(item.getQuestionText());
//                question.setQuestionType(QuestionType.valueOf(item.getQuestionType().toUpperCase()));
                question.setQuestionType(QuestionType.from(item.getQuestionType()));
//                question.setDifficulty(Difficulty.valueOf(item.getDifficulty().toUpperCase()));
                question.setDifficulty(Difficulty.from(item.getDifficulty()));
                question.setSubject(subject);
                question.setChapter(chapter);
                question.setCorrectAnswer(item.getCorrectAnswer());
                question.setExplanation(item.getExplanation());
                question.setMarks(item.getMarks());
                question.setAiGenerated(true);
                question.setCreatedBy(createdBy);

                savedQuestions.add(question);
            }
        }

        paper.setQuestions(savedQuestions);

        return questionPaperRepository.save(paper);
    }
}