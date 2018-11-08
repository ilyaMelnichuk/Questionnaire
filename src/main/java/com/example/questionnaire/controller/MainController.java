package com.example.questionnaire.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.model.Field;
import com.example.questionnaire.model.User;
import com.example.questionnaire.service.FieldService;
import com.example.questionnaire.service.UserService;

@Controller
public class MainController{
	@Autowired
	private FieldService fieldService;
    @RequestMapping(value = "/")
    public ModelAndView home() {
    	return new ModelAndView("index");
    }
    
	@RequestMapping("/fields")
	public ModelAndView getAllFields(HttpServletRequest request){
		request.setAttribute("fields",fieldService.findAllFields());
		ModelAndView model = new ModelAndView("fields");
		return model;
	}
	@RequestMapping("/fields/insert-field")
	public void insertField(@RequestAttribute Field field){
		fieldService.insertField(field);
	}
	@RequestMapping("/responses")
	public ModelAndView responses(){
		ModelAndView model = new ModelAndView("responses");
		return model;
	}
}
