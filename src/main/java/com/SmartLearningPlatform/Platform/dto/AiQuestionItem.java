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
public class AiQuestionItem {

    private String questionText;
    private String questionType;
    private String difficulty;
    private String chapterName;
//    private String correctAnswer;
//    private String explanation;
    private List<String> options;
    private Integer marks;

}
