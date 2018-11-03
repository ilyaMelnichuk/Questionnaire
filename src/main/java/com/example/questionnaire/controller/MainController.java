package com.example.questionnaire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping(value = "/hello")
    public String init() {
    	return "index";
    }
    @GetMapping(value = "/logIn")	
    public ModelAndView logIn() {
    	ModelAndView m = new ModelAndView("logIn");
    	return m;
    }
}
