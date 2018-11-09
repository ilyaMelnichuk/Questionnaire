package com.example.questionnaire.service;


import java.util.HashSet;
import java.util.Set;

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
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
    public void saveUser(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	Set<Role> roles = new HashSet<Role>();
    	roles.add(roleRepository.findByRoleName("ROLE_USER"));
    	roles.add(roleRepository.findByRoleName("ROLE_CHANGE_PASSWORD"));
        user.setRoles(roles);
        userRepository.save(user);
    }
    
    public boolean authenticateUser(User user) {
    	return (findByEmail(user.getEmail()).getPassword() == bCryptPasswordEncoder.encode(user.getPassword()));
    }
	public void changeUserPassword(User user, String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(user);
	}
	public void editUser(User user) {
		userRepository.save(user);
	}
}
