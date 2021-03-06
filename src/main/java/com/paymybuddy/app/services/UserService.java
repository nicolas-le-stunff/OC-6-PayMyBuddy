package com.paymybuddy.app.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.app.models.User;
import com.paymybuddy.app.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	public void saveUser(User user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		user.setStatut_active(true);
		user.setDate_creation(LocalDateTime.now());
		logger.info("new user" + user.getFirstName() +" "+ user.getLastName());
		userRepository.save(user);
	}

	public User userByID(int id) {
		return userRepository.getById(id);
	}

	

	public User getUserConnected() {
		Authentication authentification = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByEmail(authentification.getName());	    
	} 
	
	public void addFriend(String email) {
		User userConnected = this.getUserConnected();
		User userFind = userRepository.findByEmail(email);
		//Vérification si l'utilisateur ajouté existe + pas celui connecté
		if((userFind != null) && (userFind != userConnected)) {
			List<User> set = userConnected.getListFriend();
			set.add(userFind);
			userConnected.setListFriend(set);
		}
		userRepository.save(userConnected);	
	}
	
	//Contact de l'utilisateur connecté
	public List<User> getContactUserConnected() {
		User userConnected = this.getUserConnected();
		List<User> userContact = userConnected.getListFriend();
		List<User> listUsers = userContact;
		return listUsers;
	}
	
	
	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	//Encode password
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	

}
