package com.SmartLearningPlatform.Platform.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmissionRequest {
    private List<String> studentAnswers;
    private LocalDateTime startTime;
}