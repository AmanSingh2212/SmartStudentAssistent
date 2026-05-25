package com.SmartLearningPlatform.Platform.entity;

import com.SmartLearningPlatform.Platform.datatypes.RESULT_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Assuming you have a User entity already established
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_paper_id", nullable = false)
    private QuestionPaper questionPaper;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "attempt_student_answers", joinColumns = @JoinColumn(name = "attempt_id"))
    @Column(name = "submitted_answer", length = 3000)
    private List<String> answers;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private Integer correctAnswers;

    @Column(nullable = false)
    private Integer wrongAnswers;

    // Stores percentages cleanly (e.g., 85.5%)
    @Column(nullable = false)
    private Double accuracy;

    // Time taken in minutes or seconds (Integer is usually cleanest for tracking seconds)
    @Column(nullable = false)
    private Integer timeTaken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RESULT_STATUS resultStatus;

    @PrePersist
    protected void onStart() {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
    }
}

