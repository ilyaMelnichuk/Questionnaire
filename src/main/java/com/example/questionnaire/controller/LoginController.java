package com.example.questionnaire.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.model.User;

@Controller
public class LoginController {
    @Autowired
    //private UserService userService;
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public ModelAndView login() {
    	ModelAndView modelAndView = new ModelAndView("login");
    	return modelAndView;
    }
    
    @RequestMapping(value= {"/signup", "/signup/?"}, method = RequestMethod.GET)
    public ModelAndView signup() {
    	ModelAndView model = new ModelAndView("signup");
    	User newUser= new User();
        model.addObject("newUser", newUser);
        return model;
    }
    
    /*@RequestMapping(value="/signup", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@Valid User userToCheck, BindingResult bindingResult) {
    	ModelAndView model = new ModelAndView("signUp");
    	User user = userService.findUserByEmail(userToCheck.getEmail());
    	if(user!=null) {
    	    //code
    	}
    	if(!bindingResult.hasErrors()) {
    		userService.createUser(user);
    		model.addObject("registrationSucces", "new account has been created successfully!");
            model.addObject("user", new User());
            model.setViewName("signup");
    	}
    	return model;
    }
    */
    
}
