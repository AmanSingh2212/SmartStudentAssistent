package com.SmartLearningPlatform.Platform.request;

import com.SmartLearningPlatform.Platform.datatypes.PaperType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateQuestionPaperRequest {

    private Long subjectId;

    @NotEmpty
    private List<String> chapterNames;

    @NotNull
    @Min(1)
    private Integer noOfQuestions;

    @NotNull
    @Min(1)
    private Integer totalMarks;

    @NotNull
    @Min(1)
    private Integer duration;

    @NotBlank
    private String title;

    private String instructions;

//    @NotBlank
//    private String createdBy;

    @NotNull
    private PaperType paperType;

    private List<String> options;

}
