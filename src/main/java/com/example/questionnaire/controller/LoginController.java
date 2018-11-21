package com.example.questionnaire.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.dto.FieldDto;
import com.example.questionnaire.dto.UserDto;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.EmailService;
import com.example.questionnaire.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SessionAttributes("name")
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @Autowired 
    private EmailService emailService;
    
    @GetMapping(value = "/read-json", produces = "application/json")
    public @ResponseBody FieldDto writeJson() {
    	FieldDto fieldDto  = new FieldDto();
    	fieldDto.setLabel("label");
    	fieldDto.setType("combobox");
    	fieldDto.setRequired(true);
    	fieldDto.setisActive(true);
    	return fieldDto;
    }
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	if(error != null) {
    		model.addAttribute("message", error);
    	}
    	if(logout != null) {
    		model.addAttribute("message", "You have been logged out successfuly!");
    	}
    	return "login";
    }
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request,@ModelAttribute("name") String name, @Valid @ModelAttribute User user, BindingResult bindingResult) {
    	ModelAndView modelAndView = new ModelAndView();
    	name = user.getFirstName() + " " + user.getLastName();
    	if(bindingResult.hasErrors()) {
            modelAndView.setViewName("/login");
        	modelAndView.addObject("message", bindingResult.getAllErrors().toString());
    	}
    	return modelAndView;
    }
    
    @GetMapping(value= "/signup")
    public ModelAndView signup() {
    	ModelAndView model = new ModelAndView("signup");
        model.addObject("userToRegister", new User());
        return model;
    }
    
    @PostMapping(value="/signup", consumes = "application/json", produces = "application/json")
    public @ResponseBody String registerNewUser(@Validated(UserDto.New.class) @RequestBody UserDto userDto, BindingResult bindingResult) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	ObjectNode result = objectMapper.createObjectNode();
    	if(!bindingResult.hasErrors()) {
    		User newUser = userService.findByEmail(userDto.getEmail());
        	
        	if(newUser==null) {
        		User user = new User();
        		user.setEmail(userDto.getEmail());
        		user.setFirstName(userDto.getFirstName());
        		user.setLastName(userDto.getLastName());
        		user.setPassword(userDto.getPassword());
        		user.setPhoneNumber(userDto.getPhoneNumber());
        		
        		userService.registerUser(user);
                emailService.sendMessage(userDto.getEmail(), "Registration in Questionnaire Portal",
               	    userDto.getFirstName() + " " + userDto.getLastName() + ",\n" + "You have been successfully registered in Questionnaire Portal!");
                result.put("message", "you have been successfully signed up");
        	}else {
        		result.put("message", "user with this email has been already registered");
        	}
    	}else {
    		StringBuilder errors = new StringBuilder();
    		for (Object object : bindingResult.getAllErrors()) {
    		    if(object instanceof FieldError) {
    		        FieldError error = (FieldError) object;
                    
    		        errors.append((error.getDefaultMessage()) + "\n");
    		    }
    		}
    		result.put("message", errors.toString());
    	}
    	return result.toString();
    }
    
}
