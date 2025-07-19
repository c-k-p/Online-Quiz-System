package com.quiz.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question cannot be blank")
    private String question;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    @NotBlank(message = "Question Type cannot be blank")
    private String questionType;

    @NotEmpty(message = "Choices cannot be empty")
    @ElementCollection
    private List<String> choices;

    @NotEmpty(message = "Correct answers cannot be empty")
    @ElementCollection
    private List<String> correctAnswers;
}
