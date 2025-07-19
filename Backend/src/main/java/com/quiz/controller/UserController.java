package com.quiz.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.model.User;
import com.quiz.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) 
	{
		User user = userService.getUserProfile(jwt);
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	@GetMapping()
	public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String jwt) 
	{
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	
	// NEW: Endpoint to delete a user by ID
	// This usually requires ADMIN role. Ensure your UserService enforces this.
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) {
		try {
			// You might want to add a check here or in your UserService to ensure
			// that only admin users can perform this action, and also that an admin
			// cannot delete themselves or the last admin.
			userService.deleteUser(userId, jwt); // Pass JWT if userService needs it for authorization/validation
			return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}