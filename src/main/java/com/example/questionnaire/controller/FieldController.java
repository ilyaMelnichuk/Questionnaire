package com.example.questionnaire.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.model.Field;
import com.example.questionnaire.service.FieldService;

@RestController
public class FieldController{
	@Autowired
	private FieldService fieldService;
	
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
	/*@RequestMapping("/fields/delete-field")
	public void deleteField(@RequestAttribute Field field) {
		fieldService.deleteField(field);
	}
	@RequestMapping("/fields/update-field")
	public void insertField(@RequestAttribute Field field){
		fieldService.insertField(field);
	}*/
	
}
