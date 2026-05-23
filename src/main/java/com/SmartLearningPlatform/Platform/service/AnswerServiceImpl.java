package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Answers;
import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import com.SmartLearningPlatform.Platform.repository.AnswerRepository;
import com.SmartLearningPlatform.Platform.repository.QuestionPaperRepository;
import com.SmartLearningPlatform.Platform.service.geminiService.AiAnswerGenerationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepository answerRepository;
    private final QuestionPaperRepository questionPaperRepository;
    private final AiAnswerGenerationService aiAnswerGenerationService;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionPaperRepository questionPaperRepository, AiAnswerGenerationService aiAnswerGenerationService) {
        this.answerRepository = answerRepository;
        this.questionPaperRepository = questionPaperRepository;
        this.aiAnswerGenerationService = aiAnswerGenerationService;
    }

    @Override
    public List<List<String>> getAll() throws Exception {

        List<Answers> answers = answerRepository.findAll();

        List<List<String>> ans = answers.stream().map(Answers::getAnswers).toList();

         if(ans.isEmpty())
         {
             throw new Exception("Answers are not present");
         }

        return ans;

    }

    @Override
    public List<String> getAnswerById(Long id) throws Exception {

        Optional<Answers> answers = answerRepository.findById(id);

        if(answers.isEmpty())
        {
            throw new Exception("Answer is not present");
        }

        List<String> ans = answers.get().getAnswers();;

        return ans;
    }

    @Override
    public List<String> getAnswersByQuestionPaperId(Long questionPaperId) throws Exception{

        QuestionPaper questionPaper = questionPaperRepository.findById(questionPaperId)
                .orElseThrow(() -> new Exception("Question Paper is not present with given id"));

        Answers answers = answerRepository.findByQuestionPaperId(questionPaperId)
                .orElseGet(() -> aiAnswerGenerationService.generateAnswers(questionPaperId));

        return answers.getAnswers();
    }
}
