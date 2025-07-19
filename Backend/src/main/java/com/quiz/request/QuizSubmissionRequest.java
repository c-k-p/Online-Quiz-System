package com.quiz.request;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuizSubmissionRequest {
    private String subject;
    private Map<Long, Long> userAnswers; // Map of <Question ID, Selected Option ID>
    // Or, if your frontend sends the entire question object and selected option text:
    // private List<UserAnswer> userAnswers; // Where UserAnswer is another DTO { questionId, selectedAnswerText }
}