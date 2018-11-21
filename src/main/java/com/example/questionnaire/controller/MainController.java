package com.example.questionnaire.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.dto.FieldDto;
import com.example.questionnaire.dto.MessageDto;
import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.OptionId;
import com.example.questionnaire.entity.Type;
import com.example.questionnaire.service.FieldService;
import com.example.questionnaire.service.UserService;

@Controller
public class MainController{
	@Autowired
	private FieldService fieldService;
	@Autowired
	private UserService userService;
	/*@Autowired
	private FieldValidator fieldValidator;*/
    @RequestMapping(value = "/")
    public ModelAndView home() {
    	return new ModelAndView("index");
    }
    
	@GetMapping("/fields")
	public ModelAndView getAllFields(HttpServletRequest request){
		ModelAndView model = new ModelAndView("fields");
		return model;
	}
	
	@GetMapping(value = "/load-fields", produces = "application/json")
	public @ResponseBody FieldDto getAllActiveFields(HttpServletRequest request){
		FieldDto fieldForSurvey = new FieldDto();
		return fieldForSurvey;
	}
	
	
	@GetMapping(value = "/get-default-page", produces = "application/json")
	public @ResponseBody Page<FieldDto> getDefaultPage(){
		Page<Field> entities = fieldService.findAll(PageRequest.of(0, Integer.MAX_VALUE));
		Page<FieldDto> dtos = entities.map(new Function<Field, FieldDto>() {

			@Override
			public FieldDto apply(Field entity) {
				FieldDto dto = new FieldDto();
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.addMappings(new PropertyMap<Field, FieldDto>() {
				  @Override
				  protected void configure() {
				    map().setOptions(source.convertOptionsToString());
				  }
				});
				dto = modelMapper.map(entity, FieldDto.class);
				return dto;
			}
		});
		return dtos;
	}
	@GetMapping(value = "/get-json", produces = "application/json")
	public @ResponseBody FieldDto getJson(){
		FieldDto dto = new FieldDto();
		dto.setLabel("label");
		dto.setType("Radiobox");
		dto.setRequired(true);
		dto.setisActive(false);
		dto.setOptions("option1\ndfd");
		return dto;
	}
	
	/*@PostMapping(value = "/get-page", consumes = "application/json" ,produces = "application/json")
	public @ResponseBody Page<Field> getDefaultPage(){
		return fieldService.findAll(PageRequest.of(0, 5));
	}*/
	
	@PostMapping(value = "/save-field", consumes = "application/json", produces = "application/json")
	public @ResponseBody MessageDto saveField(@Validated(FieldDto.Save.class) @RequestBody FieldDto fieldDto, BindingResult bindingResult){
		MessageDto message = new MessageDto();
		if(!bindingResult.hasErrors()) {
			boolean isValid = false;
			for(Type t : Type.values()) {
			    if(fieldDto.getType().equals(t.name())) {
			    	isValid = true;
			    }
			}
			if(isValid) {
				Field field = new Field();
				ModelMapper mapper = new ModelMapper();
				mapper.map(fieldDto, field);
				System.out.println(field);
				System.out.println(fieldDto);
				if(!fieldDto.getOptions().equals("")) {
					String opts = fieldDto.getOptions();
					String[] options = opts.split("\n");
					System.out.println(Arrays.toString(options));
					OptionId optionId;
					Option option;
					field.setOptions(new ArrayList<Option>());
					for(int i = 0; i < options.length; i++) {/*
						optionId = new OptionId(i, fieldDto.getLabel());*/
						option = new Option( i/*optionId*/, options[i].trim(), field);
						field.addOption(option);
					}
				}
				if(!fieldDto.getOldLabel().equals("")){
					fieldService.updateField(fieldDto.getOldLabel(), field);
					message.setMessage("field has been updated");
				}else {
					if(fieldService.exists(field)) {
						message.setMessage("field with this name already exists");
					}else {
						fieldService.createField(field);
						message.setMessage("field has been created");
					}
				}
			}else {
				message.setMessage("field type is not correct");
			}
		}else {
			StringBuilder errors = new StringBuilder();
    		for (Object object : bindingResult.getAllErrors()) {
    		    if(object instanceof FieldError) {
    		        FieldError error = (FieldError) object;
    		        errors.append((error.getDefaultMessage()) + "\n");
    		    }
    		}
    		message.setMessage(errors.toString());
		}
		return message;
	}
	
	@PostMapping(value = "/delete-field", consumes = "application/json")
	public @ResponseBody MessageDto deleteField(@RequestBody MessageDto label){
		MessageDto message = new MessageDto();
		fieldService.deleteField(label.getMessage());
		message.setMessage("field \"" + label + "\" has been deleted");
		return message;
	}
	
	
	@RequestMapping("/responses")
	public ModelAndView responses(){
		ModelAndView model = new ModelAndView("responses");
		return model;
	}
}
