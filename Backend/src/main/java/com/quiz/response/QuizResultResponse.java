package com.quiz.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class QuizResultResponse {
    private Long id;
    private String subject;
    private int totalQuestions;
    private int correctAnswers;
    private double scorePercentage;
    private LocalDateTime submissionTime;
    private String username; // To display who took the quiz if needed by admin

    // Constructor to map from QuizResult entity
    public QuizResultResponse(Long id, String subject, int totalQuestions, int correctAnswers, double scorePercentage, LocalDateTime submissionTime, String username) {
        this.id = id;
        this.subject = subject;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.scorePercentage = scorePercentage;
        this.submissionTime = submissionTime;
        this.username = username;
    }
}