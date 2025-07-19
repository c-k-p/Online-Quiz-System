package com.quiz.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.config.JwtProvider;
import com.quiz.model.User;
import com.quiz.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getUserProfile(String jwt) 
	{
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long userId, String jwt) {
		userRepository.deleteById(userId);
		
	}

}