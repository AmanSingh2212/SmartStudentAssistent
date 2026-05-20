package com.SmartLearningPlatform.Platform.request;

import com.SmartLearningPlatform.Platform.entity.Subject;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChapterRequest {

    @Column(nullable = false, length = 255)
    private String name;

    // e.g. 1, 2, 3 to control ordering within a subject
    @Column(nullable = false)
    private Integer chapterIndex;

    @Column(length = 2000)
    private String description;

}
