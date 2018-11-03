package com.example.questionnaire.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.service.FieldService;

@RestController
public class MainRestController {
	@Autowired
	private FieldService fieldService;
	
	@GetMapping(value = "/")
    public String defaultMapping() {
		return "Hi";
	}
	
	@RequestMapping("/fields")
	public ModelAndView getAllFields(HttpServletRequest request){
		request.setAttribute("fields",fieldService.findAllFields());
		ModelAndView m = new ModelAndView("fields");
		return m;
	}
	@RequestMapping("/insertField")
	public void insertField(@RequestParam long id, @RequestParam String label, @RequestParam String type, @RequestParam boolean required, @RequestParam boolean isactive){
		fieldService.insertField(id, label, type, required, isactive);
	}
	@RequestMapping("/deleteField")
	public void deleteField(@RequestParam long id) {
		fieldService.deleteField(id);
	}
}
