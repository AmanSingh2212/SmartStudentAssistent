package com.SmartLearningPlatform.Platform.entity;

import com.SmartLearningPlatform.Platform.datatypes.Difficulty;
import com.SmartLearningPlatform.Platform.datatypes.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(length = 2000)
    private String correctAnswer;

    @Column(length = 4000)
    private String explanation;

    @Column(nullable = false)
    private Integer marks;

    @Column(nullable = false)
    private boolean aiGenerated;

    @Column(length = 255)
    private String createdBy;

}
