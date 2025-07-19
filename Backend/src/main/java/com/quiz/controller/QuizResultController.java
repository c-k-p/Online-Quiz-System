package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.request.QuizSubmissionRequest;
import com.quiz.response.QuizResultResponse;
import com.quiz.service.QuizResultService;

@RestController
@RequestMapping("/api/quizzes")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;

    @PostMapping("/submit-result")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Both users and admins can submit results if they take a quiz
    public ResponseEntity<QuizResultResponse> submitQuiz(@RequestBody QuizSubmissionRequest submissionRequest) {
        QuizResultResponse result = quizResultService.submitQuizResult(submissionRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/my-results")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // User can see their own results, Admin can too (but they'd likely use the admin view)
    public ResponseEntity<List<QuizResultResponse>> getUserResults() {
        List<QuizResultResponse> results = quizResultService.getUserResults();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/all-results")
    @PreAuthorize("hasRole('ADMIN')") // Only admin can see all quiz results
    public ResponseEntity<List<QuizResultResponse>> getAllQuizResults() {
        List<QuizResultResponse> results = quizResultService.getAllQuizResults();
        return ResponseEntity.ok(results);
    }
}