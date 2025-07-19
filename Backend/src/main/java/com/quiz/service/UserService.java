package com.quiz.service;


import java.util.List;

import com.quiz.model.User;



public interface UserService {

	public User getUserProfile(String jwt);
	
	public List<User> getAllUsers();

	public void deleteUser(Long userId, String jwt);
}