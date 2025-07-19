
package com.quiz.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;



import com.quiz.repository.UserRepository;


@Service
public class CustomerUserServiceImplementation implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.quiz.model.User user = userRepository.findByEmail(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("user not found with eamil"+username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

}