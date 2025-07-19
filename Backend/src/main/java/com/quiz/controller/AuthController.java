package com.quiz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.config.JwtProvider;
import com.quiz.model.User;
import com.quiz.repository.UserRepository;
import com.quiz.request.LoginRequest;
import com.quiz.response.AuthResponse;
import com.quiz.service.CustomerUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerUserServiceImplementation customerUserDetails;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String role = user.getRole();

		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist != null) {
			throw new Exception("Email is Already Used with Another Account");
		}
		
		// Create new user
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setRole(role);
		createdUser.setPassword(passwordEncoder.encode(password));
       
		User savedUser = userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = JwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Register Success");
		authResponse.setStatus(true);

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
	    String username = loginRequest.getEmail();
	    String password = loginRequest.getPassword();

	    System.out.println(username + "----" + password);

	    Authentication authentication = authenticate(username, password);
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String token = JwtProvider.generateToken(authentication);

	    // --- NEW: Fetch the user details and populate AuthResponse directly ---
	    User user = userRepository.findByEmail(username);

	    AuthResponse authResponse = new AuthResponse();
	    authResponse.setMessage("Login Success");
	    authResponse.setJwt(token);
	    authResponse.setStatus(true);
	    authResponse.setId(user.getId());             // Set user ID
	    authResponse.setEmail(user.getEmail());       // Set user email
	    authResponse.setFullName(user.getFullName()); // Set user full name
	    authResponse.setRole(user.getRole());         // Set user role (e.g., "ROLE_USER" or "ROLE_ADMIN")
	    // --- END NEW ---

	    return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	
	

	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = customerUserDetails.loadUserByUsername(username);
		
		System.out.println("sign in userDetails - "+userDetails);
		
		if(userDetails ==null)
		{
			System.out.println("sign in userDetails - null "+userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{
			System.out.println("sign in userDetails - password not match "+userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
	

}