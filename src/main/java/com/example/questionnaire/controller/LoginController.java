package com.example.questionnaire.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.example.questionnaire.dto.FieldDto;
import com.example.questionnaire.dto.MessageDto;
import com.example.questionnaire.dto.ResponseDto;
import com.example.questionnaire.dto.UserDto;
import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Response;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.EmailService;
import com.example.questionnaire.service.FieldService;
import com.example.questionnaire.service.ResponseService;
import com.example.questionnaire.service.SecurityService;
import com.example.questionnaire.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SessionAttributes("name")
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired 
    private EmailService emailService;
    
    @Autowired 
    private FieldService fieldService;
    
    @Autowired 
    private ResponseService responseService;
    
    @RequestMapping(value = "/")
    public ModelAndView home() {
		ModelAndView model = new ModelAndView("index");
    	return model;
    }
    
    @GetMapping(value = "/get-fields-to-draw", produces = "application/json")
	public @ResponseBody List<FieldDto> getJson(){
		List<Field> fieldsFromDb = fieldService.findAllActive();
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<Field, FieldDto>() {
			  @Override
			  protected void configure() {
                map().setOptions(source.convertOptionsToStringPipe());
			    map().setType(source.convertEnumToString());
			  }
	    });
		List<FieldDto> fieldsToDraw = new ArrayList<FieldDto>();
		for(Field entity: fieldsFromDb) {
			fieldsToDraw.add(mapper.map(entity, FieldDto.class));
		}
		return fieldsToDraw;
	}
    
    @MessageMapping("/resps")
	@SendTo("/topic/resps")
	public List<ResponseDto> getResponse(List<ResponseDto> responses) {
		if(!responses.isEmpty()) {
		    ModelMapper mapper = new ModelMapper();
		    Response entity;
		    long id = responseService.getMaximalId();
		    List<ResponseDto> elementsToRemove = new ArrayList<ResponseDto>();
		    for(ResponseDto dto : responses) {
			    if(!dto.getValue().equals("")) {
				    entity = mapper.map(dto, Response.class);
			        entity.setField(fieldService.findByLabel(dto.getLabel()));
			        entity.setId(id);
			        entity.setValue(dto.getValue().replace("\n", "\\n"));
			        responseService.saveResponse(entity);
			    } else {
			    	elementsToRemove.add(dto);
			    }
		    }
		    responses.removeAll(elementsToRemove);
		    
		}
		return responses;
	}
    
    
    
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
	@GetMapping(value = "/reset-password")
	public ModelAndView openResetPasswordPage(){
		ModelAndView model = new ModelAndView("resetPassword");
		return model;
	}
	
	@PostMapping(value = "/reset-password", consumes = "application/json")
	public @ResponseBody MessageDto resetPassword(@RequestBody MessageDto messageDto){
		String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		System.out.println(messageDto.getMessage());
		userService.changeUserPassword(userService.findByEmail(email), messageDto.getMessage());
		messageDto.setMessage("password has been changed");
		return messageDto;
	}
    
    @GetMapping(value="/login")
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
