package com.quiz.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionservice {
	
	
	private final QuestionRepository questionRepository;

	@Override
	public Question createQuestion(Question question) {
		
		return questionRepository.save(question);
	}

	@Override
	public List<Question> getAllQuestions() {
		// TODO Auto-generated method stub
		return questionRepository.findAll();
	}

	@Override
	public Optional<Question> getQuestionById(Long id) {
		// TODO Auto-generated method stub
		return questionRepository.findById(id);
	}

	@Override
	public List<String> getAllSubjects() {
		// TODO Auto-generated method stub
		return questionRepository.findDistinctSubject();
	}

	@Override
	public Question updateQuestion(Long id, Question question) throws NotFoundException {
		// TODO Auto-generated method stub
		Optional<Question> theQuestion =this.getQuestionById(id);	
		
		if(theQuestion.isPresent()) {
			Question updatedQuestion=theQuestion.get();
			
			updatedQuestion.setQuestion(question.getQuestion());
			
			updatedQuestion.setChoices(question.getChoices());
			
			updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
			
		     // --- !!! ADD THIS LINE TO UPDATE THE QUESTION TYPE !!! ---
	        if (question.getQuestionType() != null) { // Add null check for robustness
	            updatedQuestion.setQuestionType(question.getQuestionType());
	        }
	        // --------------------------------------------------------

	        // You might also want to update the subject if it's part of the update
	        if (question.getSubject() != null) { // Add null check for robustness
	             updatedQuestion.setSubject(question.getSubject());
	        }

			
			return questionRepository.save(updatedQuestion);
			
		}else {
			throw new ChangeSetPersister.NotFoundException();
			
		}
	}

	@Override
	public void deleteQuestion(Long id) {
		questionRepository.deleteById(id);		
	}

	@Override
	public List<Question> getQuestionsforUser(Integer numOfQuestions, String subject) {
		
		Pageable pageable = PageRequest.of(0, numOfQuestions);
		return questionRepository.findBySubject(subject, pageable).getContent();
	}

	
}
