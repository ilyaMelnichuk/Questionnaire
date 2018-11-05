package com.example.questionnaire.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.model.User;
import com.example.questionnaire.service.UserService;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public ModelAndView login() {
    	ModelAndView modelAndView = new ModelAndView("login");
    	return modelAndView;
    }
    
    @RequestMapping(value= "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
    	ModelAndView model = new ModelAndView("signup");
        model.addObject("user", new User());
        return model;
    }
    
    @RequestMapping(value="/create-user", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@ModelAttribute User user) {
    	ModelAndView model = new ModelAndView("success");
    	User newUser = userService.findUserByEmail(user.getEmail());
    	if(newUser==null) {
    		userService.createUser(user);
    	}
    	/*if(!bindingResult.hasErrors()) {
    		userService.createUser(newUser);
    		//model.addObject("registrationSucces", "new account has been created successfully!");
            //model.addObject("user", new User());
            model.setViewName("success");
    	}*/
    	return model;
    }
    
}
