package com.example.questionnaire.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseController {
	@RequestMapping("/responses")
	public ModelAndView responses(){
		ModelAndView model = new ModelAndView("responses");
		return model;
	}
}
