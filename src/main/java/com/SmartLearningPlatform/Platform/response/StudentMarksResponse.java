package com.SmartLearningPlatform.Platform.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarksResponse {

    private int totalMarks;
    private int studentMarks;
    private int percentage;

}
