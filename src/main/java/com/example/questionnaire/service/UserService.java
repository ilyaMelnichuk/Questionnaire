package com.example.questionnaire.service;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.RoleRepository;
import com.example.questionnaire.dao.UserRepository;
import com.example.questionnaire.entity.Role;
import com.example.questionnaire.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void deleteByEmail(User user) {
		userRepository.delete(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public void registerUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	Set<Role> roles = new HashSet<Role>();
    	roles.add(roleRepository.findByRoleName("ROLE_USER"));
    	roles.add(roleRepository.findByRoleName("ROLE_CHANGE_PASSWORD"));
        user.setRoles(roles);
        userRepository.save(user);
	}
	
	public boolean checkPassword(String password, String email) {
		User user = userRepository.findByEmail(email);
		System.out.println(bCryptPasswordEncoder.encode(password) + "\n" + user.getPassword());
		return bCryptPasswordEncoder.matches(password, user.getPassword());
	}
	
	public void updateUser(String oldEmail, User user) {
		userRepository.updateByEmail(oldEmail, user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
	}
	
    public void saveUser(User user) {
        userRepository.save(user);
    }
    
	public void changeUserPassword(User user, String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(user);
	}
	
	public void editUser(User user) {
		userRepository.save(user);
	}
}
