package com.quiz.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.model.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
	public User findByEmail(String email);
}