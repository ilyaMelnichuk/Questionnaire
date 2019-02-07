package com.example.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.dto.UserDto;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.EmailService;
import com.example.questionnaire.service.SecurityService;
import com.example.questionnaire.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SessionAttributes("name")
@Controller
public class EditAccountController {
	@Autowired
	UserService userService;
	@Autowired
	SecurityService securityService;
	@Autowired 
	EmailService emailService;
	
	@GetMapping(value = "/edit-profile")
	public ModelAndView editProfile(){
		ModelAndView model = new ModelAndView("editProfile");
        return model;
	}
	
	@GetMapping(value = "/load-user-data", produces="application/json")
	public @ResponseBody UserDto loadUserData() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.findByEmail(email);
		UserDto userDto = new UserDto(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
        return userDto;
	}
	
	@PostMapping(value = "/check-changes", consumes = "application/json", produces = "application/json")
    public @ResponseBody String checkChanges(@Validated(UserDto.EditProfile.class) @RequestBody UserDto userDto, BindingResult bindingResult) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	ObjectNode result = objectMapper.createObjectNode();
    	if(!bindingResult.hasErrors()) {
        	//check for user with given email in database. If exists we check this user and current principal for equality.
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		String email = auth.getName();
        	if(userService.existsByEmail(userDto.getEmail())) {
        		if(userDto.getEmail().equals(email)){
        			User user = new User();
        			user.setEmail(email);
        			user.setFirstName(userDto.getFirstName());
        			user.setLastName(userDto.getLastName());
        			user.setPhoneNumber(userDto.getPhoneNumber());
        			userService.updateUser(email, user);
        			result.put("message", "changes have been submitted successfully");
        		}else {
        			result.put("message", "user with this email has been already registered");
        		}
        	}else {
        		User user = new User();
        		user.setEmail(userDto.getEmail());
        		user.setFirstName(userDto.getFirstName());
        		user.setLastName(userDto.getLastName());
        		user.setPhoneNumber(userDto.getPhoneNumber());
        		userService.updateUser(email, user);
        	    SecurityContextHolder.getContext().setAuthentication(auth);
        	    result.put("message", "your email address has been changed");
        	}
    	}else {
    		StringBuilder errors = new StringBuilder();
    		for (Object object : bindingResult.getAllErrors()) {
    		    if(object instanceof FieldError) {
    		        FieldError error = (FieldError) object;
                    
    		        errors.append((error.getDefaultMessage()));
    		    }
    		}
    		result.put("message", errors.toString());
    	}
    	return  result.toString();
    }
	
	@GetMapping(value = "/change-password")
	public ModelAndView changePassword() {
		ModelAndView model = new ModelAndView("changePassword");
		return model;
	}
	
	@PostMapping(value = "/change-password", consumes="application/json", produces="application/json")
	public @ResponseBody String setNewPassword(@Validated(UserDto.ChangePassword.class) @RequestBody UserDto userDto, BindingResult bindingResult) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode result = objectMapper.createObjectNode();
		if(!bindingResult.hasErrors()) {
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String email = auth.getName();
		    if(userService.checkPassword(userDto.getPassword(), email)) {
		    	User user = userService.findByEmail(email);
		    	userService.changeUserPassword(user, userDto.getNewPassword());
		        emailService.sendMessage(user.getEmail(), "Changing password in Questionnaire Portal",
                user.getFirstName() + " " + user.getLastName() + ",\n" + " You have successfully changed your password in Questionnaire Portal!");
		        result.put("message", "you have successfully changed your password");
		    }else {
		    	result.put("message", "current password is not correct");
		    }
		}else {
			StringBuilder errors = new StringBuilder();
    		for (Object object : bindingResult.getAllErrors()) {
    		    if(object instanceof FieldError) {
    		        FieldError error = (FieldError) object;
                    
    		        errors.append((error.getDefaultMessage()));
    		    }
    		}
    		result.put("message", errors.toString());
		}
		return result.toString();
	}
	
	@ModelAttribute("name")
	public String setUpUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.findByEmail(email);
		return user.getFirstName() + " " + user.getLastName();
	}
}
