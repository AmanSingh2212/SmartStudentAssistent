package com.SmartLearningPlatform.Platform.datatypes;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public static Difficulty from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Difficulty cannot be null or blank");
        }

        String normalized = value.trim().toUpperCase()
                .replace(" ", "_")
                .replace("-", "_");

        return switch (normalized) {
            case "EASY" -> EASY;
            case "MEDIUM", "MODERATE" -> MEDIUM;
            case "HARD", "DIFFICULT" -> HARD;
            default -> throw new IllegalArgumentException("Unsupported Difficulty: " + value);
        };
    }
}
