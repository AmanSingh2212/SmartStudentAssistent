package com.SmartLearningPlatform.Platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_paper_id", nullable = false)
    private QuestionPaper questionPaper;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "paper_answers", joinColumns = @JoinColumn(name = "answer_id"))
    @Column(name = "answer_text", length = 4000)
    private List<String> answers = new ArrayList<>();

}
