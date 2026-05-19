package com.SmartLearningPlatform.Platform.request;

import com.SmartLearningPlatform.Platform.datatypes.BOARD;
import com.SmartLearningPlatform.Platform.datatypes.TARGETEXAM;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 15)
    private String mobile;

    private String standard; // e.g., "10th", "11th", "12th"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BOARD board;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private TARGETEXAM targetExam;

}
