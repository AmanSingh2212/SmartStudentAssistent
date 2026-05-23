package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Answers;

import java.util.List;

public interface AnswerService {

     List<List<String>> getAll() throws Exception;

     List<String> getAnswerById(Long id) throws Exception;

     List<String> getAnswersByQuestionPaperId(Long questionPaperId) throws Exception;

}
