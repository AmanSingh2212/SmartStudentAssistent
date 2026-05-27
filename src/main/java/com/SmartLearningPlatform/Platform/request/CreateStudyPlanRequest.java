package com.SmartLearningPlatform.Platform.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudyPlanRequest {

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

}
