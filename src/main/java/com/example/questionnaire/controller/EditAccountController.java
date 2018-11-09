package com.example.questionnaire.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.model.Password;
import com.example.questionnaire.model.User;
import com.example.questionnaire.service.EmailService;
import com.example.questionnaire.service.SecurityService;
import com.example.questionnaire.service.UserService;

@Controller
public class EditAccountController {
	@Autowired
	UserService userService;
	@Autowired
	SecurityService securityService;
	@Autowired 
	EmailService emailService;
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public ModelAndView forgotPassword(HttpServletRequest request, @ModelAttribute("user") User user){
		ModelAndView model = new ModelAndView("forgotPassword");
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ModelAndView sendResetMail(HttpServletRequest request, @ModelAttribute User user){
		ModelAndView model = new ModelAndView("forgotPassword");
		user = userService.findByEmail(user.getEmail());
		if(user == null) {
			model.addObject("message", "No user with this email!");
		}else {
			String token = UUID.randomUUID().toString();
			securityService.createPasswordResetToken(user, token);
			emailService.sendMessage(user.getEmail(), "reset password","Please, open this link to reset your password:  " + securityService.getApplicationUrl(request) + "/reset-password?mail=" + 
				      user.getEmail() + "&token=" + token);
		}
		return model;
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public String resetPassword(@RequestParam("mail") String mail, @RequestParam("token") String token){
		String result = securityService.validatePasswordResetToken(mail, token);
		if (result != null) {
			        return "redirect:/login";
	    }
		return "redirect:/change-password";
	}
	
	
	@RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
	public ModelAndView editProfile(){
		ModelAndView model = new ModelAndView("editProfile");
		User userToChange = new User();
        model.addObject("userToChange", userToChange);
        return model;
	}
	
	@RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
    public ModelAndView checkChanges(@ModelAttribute("userToChange") User userToChange, BindingResult bindingResult) {
    	ModelAndView model = new ModelAndView("editProfile");
    	if(!bindingResult.hasErrors()) {
    		User newUser = userService.findByEmail(userToChange.getEmail());
    		
        	//check for user with given email in database. If exists we check this user and current principal for equality.
        	if(newUser==null) {
                
        	}else {
        		User user = userService.findByEmail(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        		if(newUser.getEmail() == user.getEmail()){
        			userService.saveUser(user);
        			model.addObject("message", "Changes have been submitted successfully!");
        		}else {
        		    model.addObject("message", "User with this email has been already registered!");
        		}
        	}
    	}
    	model.addObject("userToChange", userToChange);
    	model.addObject("message", "Changes have been submitted successfully!");
    	return model;
    }
	
	@RequestMapping(value = "/change-password", method = RequestMethod.GET)
	public ModelAndView changePassword(@ModelAttribute("password") Password password) {
		ModelAndView model = new ModelAndView("/changePassword", "password", password);
		model.addObject("message", "Enter your new password");
		return model;
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ModelAndView setNewPassword(@ModelAttribute Password password) {
		ModelAndView model = new ModelAndView("/change-password");
		model.addObject("message", "Your password was changed!");
		String email = ((com.example.questionnaire.model.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
		
		User user = userService.findByEmail(email);
		userService.changeUserPassword(user, password.getPassword());
		
		emailService.sendMessage(user.getEmail(), "Changing password in Questionnaire Portal",
        user.getFirstName() + " " + user.getLastName() + ",\n" + "in Questionnaire Portal!");
		
		model.addObject("password", password);
		model.addObject("message", "You have successfully change your password!");
		return model;
	}
}
