package com.SmartLearningPlatform.Platform.entity;


import com.SmartLearningPlatform.Platform.datatypes.PaperType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    // Keep as simple strings, or you can link to Subject entity if you prefer
    @ManyToOne
    private Subject subject;

    // One paper -> many questions
//    @OneToMany
//    @JoinTable(
//            name = "question_paper_questions",
//            joinColumns = @JoinColumn(name = "question_paper_id"),
//            inverseJoinColumns = @JoinColumn(name = "question_id")
//    )
//    private List<Question> questions = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "question_paper_questions",
            joinColumns = @JoinColumn(name = "question_paper_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    @Column(nullable = false)
    private Integer totalMarks;

    // Example: duration in minutes; or map java.time.Duration if your JPA provider supports it
    @Column(nullable = false)
    private Integer duration; // e.g. 90 for 1.5 hours

    @Column(nullable = false)
    private boolean generatedByAI;

//    @Column(length = 255)
//    private String createdBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaperType paperType;

    @Column(length = 4000)
    private String instructions;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
