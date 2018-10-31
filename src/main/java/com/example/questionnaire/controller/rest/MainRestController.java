package com.example.questionnaire.controller.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.model.Field;
import com.example.questionnaire.service.FieldService;

@RestController
public class MainRestController {
	@Autowired
	private FieldService fieldService;
	
	@GetMapping(value = "/")
    public String defaultMapping() {
		return "Hi";
	}
	
	@GetMapping("/findAllFields")
	public Collection<Field> getAllFields(){
		
		return fieldService.findAllFields();
	}
}
