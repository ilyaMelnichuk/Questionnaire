package com.example.questionnaire.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import com.example.questionnaire.dto.ResponseDto;
import com.example.questionnaire.dto.ResponseList;
import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.Response;
import com.example.questionnaire.entity.Type;
import com.example.questionnaire.service.FieldService;
import com.example.questionnaire.service.ResponseService;
import com.example.questionnaire.service.UserService;

@Controller
public class MainController{
	@Autowired
	private FieldService fieldService;
	@Autowired
	private UserService userService;
	@Autowired
	private ResponseService responseService;
	@Autowired
	private SimpMessagingTemplate template;
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
		Page<FieldDto> dtos = entities.map(getConverter());
		return dtos;
	}
	
	@GetMapping(value = "/get-all-responses", produces = "application/json")
	public @ResponseBody List<ResponseDto> getAllResponses(){
		List<Response> entities = responseService.findAllResponses();
		List<ResponseDto> dtos = new ArrayList<ResponseDto>();
		ResponseDto dto = new ResponseDto();
		for(Response r: entities) {
		    dto.setId(r.getId());
		    dto.setLabel(r.getField().getLabel());
		    dto.setValue(r.getValue());
		    dtos.add(dto);
		}
		return dtos;
	}
	
	/*@GetMapping(value = "/get-json", produces = "application/json")
	public @ResponseBody ResponseList<ResponseDto> geton(){
		ResponseDto dto = new ResponseDto();
		dto.setId(12);
		dto.setLabel("fds");
		dto.setValue("fds");
		ResponseList<ResponseDto> dtos = new ResponseList<ResponseDto>();
		dtos.setResponse(Arrays.asList(dto));
		return dtos;
	}*/
	
	/*@PostMapping(value = "/get-page", consumes = "application/json" ,produces = "application/json")
	public @ResponseBody Page<Field> getDefaultPage(@RequestBody MessageDto message){
	    String page = message.getMessage();
	    int pageNumber = Integer.parseInt(message.substring(0, message.indexOf(",")));
	    if(message.substring(message.indexOf(",")).equals("all")){
	        return fieldService.findAll(PageRequest.of(0, Integer.MAX_VALUE));
	    }else{
	        int pageSize = Integer.parseInt(message.substring(message.indexOf(",")));
	        return fieldService.findAll(PageRequest.of(pageNumber, pageSize));
	    }
		
	}*/
	
/*	@PostMapping(value = "send-response", consumes = "application/json", produces = "application/json")
	public @ResponseBody MessageDto getResponses(@RequestBody @Valid ResponseList<ResponseDto> responses, BindingResult bindingResult) {
		MessageDto message = new MessageDto();
		if(bindingResult.hasErrors()) {
			message.setMessage("please fill in all required fields");
		}else {
		    ModelMapper mapper = new ModelMapper();
		    Response entity;
		    long id = responseService.getMaximalId();
		    for(ResponseDto dto : responses.getResponse()) {
		        entity = mapper.map(dto, Response.class);
		        entity.setField(fieldService.findByLabel(dto.getLabel()));
		        entity.setId(id);
		        entity.setValue(dto.getValue().replace("\n", "\\n"));
		        responseService.saveResponse(entity);
		    }
		    //sendResponse(responses);
		    message.setMessage("success");
		}
		return message;
	}*/
	
	@PostMapping(value = "/save-field", consumes = "application/json", produces = "application/json")
	public @ResponseBody MessageDto saveField(@Validated(FieldDto.Save.class) @RequestBody FieldDto fieldDto, BindingResult bindingResult){
		MessageDto message = new MessageDto();
		if(!bindingResult.hasErrors()) {
			boolean isValid = false;
			Type type = null;
			for(Type t : Type.values()) {
			    if(fieldDto.getType().equals(t.getText())) {
			    	isValid = true;
			    	type = t;
			    	break;
			    }
			}
			if(isValid) {
				Field field = new Field();
				ModelMapper mapper = new ModelMapper();
				mapper.map(fieldDto, field);
				field.setType(type);
				System.out.println(field);
				System.out.println(fieldDto);
				if(!fieldDto.getOptions().equals("")) {
					String opts = fieldDto.getOptions();
					String[] options = opts.split("\n");
					System.out.println(Arrays.toString(options));
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
	@PostMapping(value = "send-response", consumes = "application/json", produces = "application/json")
	public @ResponseBody MessageDto getResponse(@RequestBody List<ResponseDto> responses) {
		MessageDto message = new MessageDto();
		ModelMapper mapper = new ModelMapper();
		Response entity;
		long id = responseService.getMaximalId();
		for(ResponseDto dto : responses) {
		    entity = mapper.map(dto, Response.class);
		    entity.setField(fieldService.findByLabel(dto.getLabel()));
		    entity.setId(id);
		    entity.setValue(dto.getValue().replace("\n", "\\n"));
		    responseService.saveResponse(entity);
		}
		
		return message;
	}
	
	
	@RequestMapping("/responses")
	public ModelAndView responses(){
		ModelAndView model = new ModelAndView("responses");
		return model;
	}
	
	@GetMapping("/success")
	public ModelAndView success(){
		ModelAndView model = new ModelAndView("success");
		return model;
	}
	
	/*public void sendResponse(ResponseList<ResponseDto> responses){
		template.convertAndSend("/topic/responses", responses);
	}*/
	
	
	private Function<Field, FieldDto> getConverter(){
		return new Function<Field, FieldDto>() {

			@Override
			public FieldDto apply(Field entity) {
				FieldDto dto = new FieldDto();
				ModelMapper mapper = getFieldToFieldDtoMapper();
				dto = mapper.map(entity, FieldDto.class);
				return dto;
			}
		};
	}
	private ModelMapper getFieldToFieldDtoMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<Field, FieldDto>() {
			  @Override
			  protected void configure() {
			    map().setOptions(source.convertOptionsToStringNL());
			    map().setType(source.convertEnumToString());
			  }
	    });
		return mapper;
	}
}
