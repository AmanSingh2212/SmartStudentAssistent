package com.SmartLearningPlatform.Platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiQuestionPaperResponse {

    private String title;
    private Integer totalMarks;
    private Integer duration;
    private String instructions;
    private String paperType;
    private List<AiQuestionItem> questions;

}
