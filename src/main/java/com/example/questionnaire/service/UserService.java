package com.example.questionnaire.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.RoleRepository;
import com.example.questionnaire.dao.UserRepository;
import com.example.questionnaire.model.Role;
import com.example.questionnaire.model.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRoleName("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.createUser(user.getEmail(), user.getPassword() ,user.getFirstName(), user.getLastName(), user.getPhoneNumber());
	}
	
	public void editUser(User user) {
		userRepository.editUser(user.getEmail(),user.getFirstName(),user.getLastName(),user.getPhoneNumber());
	}
	
	public void changeUsersPassword(String email, String password) {
	    userRepository.changeUsersPassword(email, bCryptPasswordEncoder.encode(password));
	}
	
	
	
}
