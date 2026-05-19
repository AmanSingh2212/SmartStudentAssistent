package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.Subject;
import com.SmartLearningPlatform.Platform.repository.SubjectRepository;
import com.SmartLearningPlatform.Platform.request.UpdateSubjectRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    @Override
    public Subject createSubject(Subject subject) throws Exception {

        if (subject.getName() == null || subject.getName().isBlank()) {
            throw new Exception("Subject name cannot be null or empty");
        }

        if (subject.getStandard() == null) {
            throw new Exception("Subject standard cannot be null or empty");
        }

        if (subject.getDescription() == null) {
            throw new Exception("Subject description cannot be null or empty");
        }

        return subjectRepository.save(subject);

    }

    @Override
    public List<Subject> getSubjectsByStandard(String standard) throws Exception {

           List<Subject> subjects = subjectRepository.findAllByStandard(standard);

           if(subjects.isEmpty())
           {
               throw new Exception("Subjects are not present with given standard");
           }

           return subjects;

    }

    @Override
    public Subject updateSubject(UpdateSubjectRequest updateSubjectRequest, Long id) throws Exception {

        // 1. Fetch the existing subject or throw a clean exception if it doesn't exist
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new Exception("Subject not found with id: " + id));

        // 2. Update the fields if they are provided in the request
        if (updateSubjectRequest.getName() != null) {
            existingSubject.setName(updateSubjectRequest.getName());
        }

        if (updateSubjectRequest.getStandard() != null) {
            existingSubject.setStandard(updateSubjectRequest.getStandard());
        }

        // 3. Save the updated entity back to the database and return it
        return subjectRepository.save(existingSubject);

    }

    @Override
    public void deleteSubject(Long id) throws Exception {

        subjectRepository.deleteById(id);

    }
}
