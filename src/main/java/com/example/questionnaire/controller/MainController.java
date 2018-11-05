package com.example.questionnaire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController{
	
    @RequestMapping(value = "/")
    public ModelAndView home() {
    	return new ModelAndView("index");
    }
    /*@RequestMapping(value="/", method = RequestMethod.POST)
    public ModelAndView getResults() {
    	ModelAndView model = new ModelAndView("index");
    	return model;
    }*/
    
}
