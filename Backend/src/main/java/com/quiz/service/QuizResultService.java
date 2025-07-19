package com.quiz.service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quiz.model.Question;
import com.quiz.model.QuizResult;
import com.quiz.model.User;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizResultRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.QuizSubmissionRequest;
import com.quiz.response.QuizResultResponse;

@Service
public class QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private QuestionRepository questionRepository; // Assuming you have this to get questions

    @Autowired
    private UserRepository userRepository; // To get the User entity

    @Transactional
    public QuizResultResponse submitQuizResult(QuizSubmissionRequest submissionRequest) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUsername);
                //.orElseThrow(() -> new RuntimeException("Authenticated user not found.")); // Should not happen if authenticated

        Map<Long, Long> userAnswers = submissionRequest.getUserAnswers();
        String subject = submissionRequest.getSubject();

        // 1. Fetch the actual questions to verify answers
        List<Question> questions = questionRepository.findAllById(userAnswers.keySet());

        int correctAnswers = 0;
        int totalQuestions = questions.size();

        for (Question question : questions) {
            Long userAnswerId = userAnswers.get(question.getId());

            if (userAnswerId != null && userAnswerId >= 0 && userAnswerId < question.getChoices().size()) {
                String selectedChoice = question.getChoices().get(userAnswerId.intValue());

                if (question.getCorrectAnswers().contains(selectedChoice)) {
                    correctAnswers++;
                }
            }
        }

        double scorePercentage = (totalQuestions > 0) ? ((double) correctAnswers / totalQuestions) * 100 : 0;

        QuizResult quizResult = new QuizResult(
                currentUser,
                subject,
                totalQuestions,
                correctAnswers,
                scorePercentage
        );

        QuizResult savedResult = quizResultRepository.save(quizResult);

        return new QuizResultResponse(
                savedResult.getId(),
                savedResult.getSubject(),
                savedResult.getTotalQuestions(),
                savedResult.getCorrectAnswers(),
                savedResult.getScorePercentage(),
                savedResult.getSubmissionTime(),
                savedResult.getUser().getEmail() // Or getUser().getUsername()
        );
    }

    public List<QuizResultResponse> getUserResults() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUsername);
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found."));

        List<QuizResult> results = quizResultRepository.findByUserOrderBySubmissionTimeDesc(currentUser);

        return results.stream()
                .map(result -> new QuizResultResponse(
                        result.getId(),
                        result.getSubject(),
                        result.getTotalQuestions(),
                        result.getCorrectAnswers(),
                        result.getScorePercentage(),
                        result.getSubmissionTime(),
                        result.getUser().getEmail()
                ))
                .collect(Collectors.toList());
    }

    // Admin endpoint to get all quiz results
    public List<QuizResultResponse> getAllQuizResults() {
        List<QuizResult> results = quizResultRepository.findAll();
        return results.stream()
                .map(result -> new QuizResultResponse(
                        result.getId(),
                        result.getSubject(),
                        result.getTotalQuestions(),
                        result.getCorrectAnswers(),
                        result.getScorePercentage(),
                        result.getSubmissionTime(),
                        result.getUser().getEmail()
                ))
                .collect(Collectors.toList());
    }
}