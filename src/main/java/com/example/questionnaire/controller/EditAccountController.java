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
public class EditAccountController {
	@Autowired
	UserService userService;
	@RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
	public ModelAndView editProfile(@ModelAttribute User user){
		ModelAndView model = new ModelAndView("editProfile");
		User userToChange = new User();
		userToChange.setEmail(user.getEmail());
		userToChange.setFirstName(user.getFirstName());
		userToChange.setLastName(user.getLastName());
		userToChange.setPhoneNumber(user.getPhoneNumber());
        model.addObject("userToChange", userToChange);
        return model;
	}
	@RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
    public ModelAndView checkChanges(@ModelAttribute User user, @ModelAttribute User userToChange, BindingResult bindingResult) {
    	ModelAndView model = new ModelAndView("signup");
    	if(!bindingResult.hasErrors()) {
    		User newUser = userService.findByEmail(userToChange.getEmail());
        	
        	if(newUser==null) {
        		userToChange.setPassword(user.getPassword());
        		userService.saveUserWithoutEncoding(user);

            	model.setViewName("/signup/success");
        	}else {
        		model.addObject("error", "User with this email has been already registered!");
        	}
    	}
    	return model;
    }
}
