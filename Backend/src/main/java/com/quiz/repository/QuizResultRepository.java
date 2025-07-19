package com.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.model.QuizResult;
import com.quiz.model.User;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUserOrderBySubmissionTimeDesc(User user);
    // You might also need methods to find results by subject, or for admin to see all results.
}