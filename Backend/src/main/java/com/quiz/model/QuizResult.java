package com.quiz.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Link to the User who took the quiz

    private String subject; // e.g., "Java", "Python"
    private int totalQuestions;
    private int correctAnswers;
    private double scorePercentage;

    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    // Optional: Store the user's answers for review (can be complex if questions have multiple choice types etc.)
    // For simplicity, we'll just store overall metrics.
    // If you need to store specific user answers, you might need another entity like QuizAttemptQuestion

    public QuizResult(User user, String subject, int totalQuestions, int correctAnswers, double scorePercentage) {
        this.user = user;
        this.subject = subject;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.scorePercentage = scorePercentage;
        this.submissionTime = LocalDateTime.now(); // Set current timestamp
    }
}