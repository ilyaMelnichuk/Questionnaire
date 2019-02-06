package com.example.questionnaire.controller;

import java.security.Principal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.dto.MessageDto;
import com.example.questionnaire.dto.UserDto;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.EmailService;
import com.example.questionnaire.service.SecurityService;
import com.example.questionnaire.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SessionAttributes("name")
@Controller
public class LoginController {
	@Autowired 
	private BCryptPasswordEncoder crypto = new BCryptPasswordEncoder();
    @Autowired
    private UserService userService;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired 
    private EmailService emailService;
    
    @GetMapping(value = "/forgot-password")
	public ModelAndView forgotPassword(){
		ModelAndView model = new ModelAndView("forgotPassword");
		return model;
	}
	
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MessageDto sendResetEmail(@RequestBody UserDto userDto, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
		User user = userService.findByEmail(userDto.getEmail());
		if(user == null) {
			messageDto.setMessage("no user with this email");
		}else {
			String token = UUID.randomUUID().toString();
			securityService.createPasswordResetToken(user, token);
			messageDto.setMessage("reset link has been sent");
			emailService.sendMessage(userDto.getEmail(), "reset password","Please, open this link to reset your password:  " + securityService.getApplicationUrl(request) + "/validate-token?mail=" + 
				      user.getEmail() + "&token=" + token);
		}
		return messageDto;
	}
	
	@GetMapping(value = "/validate-token")
	public String validateToken(@RequestParam("mail") String mail, @RequestParam("token") String token){
		String result = securityService.validatePasswordResetToken(mail, token);
		if (result != null) {
			        return "redirect:/login";
	    }
		return "redirect:/reset-password";
	}
	
	@PreAuthorize("hasRole('ROLE_CHANGE_PASSWORD')")
	@GetMapping(value = "/reset-password")
	public ModelAndView openResetPasswordPage(){
		ModelAndView model = new ModelAndView("resetPassword");
		return model;
	}
	
	@PostMapping(value = "/reset-password", consumes = "application/json")
	public String resetPassword(@RequestBody MessageDto messageDto){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.changeUserPassword(user, messageDto.getMessage());
		return "redirect:/login";
	}
    
	@GetMapping(value="/login")
    public String login(Principal principal) {
    	if(principal == null) {
    		return "login";
    	}else {
    		return "redirect:/";
    	}
    }
	
    @GetMapping(value="/login-error")
    public String login(Model model, String error, String logout) {
    	if(error != null) {
    		model.addAttribute("message", error);
    	}
    	if(logout != null) {
    		model.addAttribute("message", "You have been logged out successfuly!");
    	}
    	return "login"; 	
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
        		ModelMapper modelMapper = new ModelMapper();
        		User user = modelMapper.map(userDto, User.class);
        		
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
    
    @GetMapping("/access-denied")
    public String accessDenied() {
    	return "accessDenied";
    }
    
}
