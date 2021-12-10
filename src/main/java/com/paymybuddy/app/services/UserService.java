package com.paymybuddy.app.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.app.configuration.Security;
import com.paymybuddy.app.models.User;
import com.paymybuddy.app.repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
		
	
	public void saveUser(User user) {
		user.setDate_creation(LocalDate.now());
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		userRepository.save(user);
	}
	

	private PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();	
	}

}
