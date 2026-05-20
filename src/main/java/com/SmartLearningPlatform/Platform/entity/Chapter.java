package com.SmartLearningPlatform.Platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chapters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    // e.g. 1, 2, 3 to control ordering within a subject
    @Column(nullable = false)
    private Integer chapterIndex;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(length = 2000)
    private String description;

    // in minutes; you can also use Duration and convert in code
    private Integer estimatedStudyTime;

}