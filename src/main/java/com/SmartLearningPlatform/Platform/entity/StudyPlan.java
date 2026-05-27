package com.SmartLearningPlatform.Platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_plan")
public class StudyPlan {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "target_exam", nullable = false, length = 100)
        private String targetExam;

        @Column(name = "daily_hours", nullable = false)
        private Integer dailyHours;

        @Column(name = "start_date", nullable = false)
        private LocalDate startDate;

        @Column(name = "end_date", nullable = false)
        private LocalDate endDate;

        @Column(name = "goal", nullable = false, length = 500)
        private String goal;

        @Lob
        @Column(name = "ai_generated_plan", columnDefinition = "TEXT")
        private String aiGeneratedPlan;

}
