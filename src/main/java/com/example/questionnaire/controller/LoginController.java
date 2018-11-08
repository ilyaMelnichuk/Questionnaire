package com.example.questionnaire.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.model.User;
import com.example.questionnaire.service.EmailService;
import com.example.questionnaire.service.UserService;

//@SessionAttributes("user")
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @Autowired 
    private EmailService emailService;
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	if(error != null) {
    		model.addAttribute("errorMsg", error);
    	}
    	if(logout != null) {
    		model.addAttribute("logoutMsg", "You have been logged out successfuly!");
    	}
    	return "login";
    }
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView authenticate(@Valid @ModelAttribute User user, BindingResult bindingResult) {
    	ModelAndView model = new ModelAndView("/fields");
    	model.addObject("user", user);
    	return model;
    }
    
    @RequestMapping(value= "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
    	ModelAndView model = new ModelAndView("signup");
        model.addObject("userToRegister", new User());
        return model;
    }
    
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@Valid @ModelAttribute User userToRegister, BindingResult bindingResult) {
    	ModelAndView model = new ModelAndView("signup");
    	if(!bindingResult.hasErrors()) {
    		User newUser = userService.findByEmail(userToRegister.getEmail());
        	
        	if(newUser==null) {
        		userService.saveUser(userToRegister);
                emailService.sendMessage(userToRegister.getEmail(), "Registration in Questionnaire Portal",
               	    userToRegister.getFirstName() + " " + userToRegister.getLastName() + ",\n" + "You have been successfully registered in Questionnaire Portal!",
               	    "Questionnaire.portal");
            	model.setViewName("/signup/success");
        	}else {
        		model.addObject("error", "User with this email has been already registered!");
        	}
    	}
    	return model;
    }
    
}
