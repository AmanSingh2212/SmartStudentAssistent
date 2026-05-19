package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Subject;
import com.SmartLearningPlatform.Platform.request.UpdateSubjectRequest;

import java.util.List;

public interface SubjectService {

    Subject createSubject(Subject subject) throws Exception;

    List<Subject> getSubjectsByStandard(String standard) throws Exception;

    Subject updateSubject(UpdateSubjectRequest updateSubjectRequest, Long id) throws Exception;

    void deleteSubject(Long id) throws Exception;

}
