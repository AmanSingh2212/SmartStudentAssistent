package com.SmartLearningPlatform.Platform.datatypes;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum QuestionType {
                MCQ,
                SHORT_ANSWER,
                LONG_ANSWER,
                NUMERICAL;

                @JsonCreator
                public static QuestionType from(String value) {
                        if (value == null || value.isBlank()) {
                                throw new IllegalArgumentException("QuestionType cannot be null or blank");
                        }

                        String normalized = value.trim().toUpperCase()
                                .replace(" ", "_")
                                .replace("-", "_");

                        return switch (normalized) {
                                case "MCQ", "MULTIPLE_CHOICE", "MULTIPLE_CHOICE_QUESTION" -> MCQ;
                                case "SUBJECTIVE", "SHORT", "SHORT_ANSWER", "SHORT_QUESTION" -> SHORT_ANSWER;
                                case "LONG", "LONG_ANSWER", "DESCRIPTIVE", "ESSAY" -> LONG_ANSWER;
                                case "NUMERICAL", "PROBLEM_SOLVING", "CALCULATION_BASED" -> NUMERICAL;
                                default -> Arrays.stream(values())
                                        .filter(v -> v.name().equalsIgnoreCase(normalized))
                                        .findFirst()
                                        .orElseThrow(() -> new IllegalArgumentException("Unsupported QuestionType: " + value));
                        };
                }
        }


