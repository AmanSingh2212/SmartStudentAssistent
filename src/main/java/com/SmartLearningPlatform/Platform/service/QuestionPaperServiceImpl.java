package com.SmartLearningPlatform.Platform.service;

 import com.SmartLearningPlatform.Platform.entity.QuestionPaper;
import com.SmartLearningPlatform.Platform.repository.QuestionPaperRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionPaperServiceImpl implements QuestionPaperService{

    private final QuestionPaperRepository questionPaperRepository;

    public QuestionPaperServiceImpl(QuestionPaperRepository questionPaperRepository) {
        this.questionPaperRepository = questionPaperRepository;
    }

    @Override
    public QuestionPaper findById(Long id) throws Exception {

        Optional<QuestionPaper> questionPaperOptional = questionPaperRepository.findById(id);

        if(questionPaperOptional.isEmpty())
        {
            throw new Exception("Question Paper not found with given id");
        }

        return questionPaperOptional.get();
    }
}
