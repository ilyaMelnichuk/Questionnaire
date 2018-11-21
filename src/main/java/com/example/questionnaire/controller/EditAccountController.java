package com.example.questionnaire.controller;

import java.util.Arrays;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public ModelAndView forgotPassword(){
		ModelAndView model = new ModelAndView("forgotPassword");
		return model;
	}

	@PostMapping(value = "/forgot-password", consumes = "application/json", produces = "application/json")
	public @ResponseBody String sendResetMail(@RequestBody UserDto userDto, HttpServletRequest request){
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode result = objectMapper.createObjectNode();
		
		User user = userService.findByEmail(userDto.getEmail());
		if(user == null) {
			result.put("message", "no user with this email");
			System.out.println("no user");
		}else {
			String token = UUID.randomUUID().toString();
			securityService.createPasswordResetToken(user, token);
			result.put("message", "reset link has been sent");
			emailService.sendMessage(userDto.getEmail(), "reset password","Please, open this link to reset your password:  " + securityService.getApplicationUrl(request) + "/reset-password?mail=" + 
				      user.getEmail() + "&token=" + token);
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public String resetPassword(@RequestParam("mail") String mail, @RequestParam("token") String token){
		String result = securityService.validatePasswordResetToken(mail, token);
		if (result != null) {
			        return "redirect:/login";
	    }
		return "redirect:/change-password";
	}
	
	
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
        		          auth = new UsernamePasswordAuthenticationToken(
        			      user, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
        			      new SimpleGrantedAuthority("ROLE_CHANGE_PASSWORD")));
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
		
		    User user = userService.findByEmail(email);
		    userService.changeUserPassword(user, userDto.getNewPassword());
		
		    emailService.sendMessage(user.getEmail(), "Changing password in Questionnaire Portal",
            user.getFirstName() + " " + user.getLastName() + ",\n" + " You have successfully changed your password in Questionnaire Portal!");
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
}
