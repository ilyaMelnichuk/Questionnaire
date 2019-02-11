package com.example.questionnaire.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.questionnaire.dto.FieldDto;
import com.example.questionnaire.dto.MessageDto;
import com.example.questionnaire.dto.PollDto;
import com.example.questionnaire.dto.ResponseDto;
import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.Poll;
import com.example.questionnaire.entity.Response;
import com.example.questionnaire.entity.Type;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.FieldService;
import com.example.questionnaire.service.PollService;
import com.example.questionnaire.service.UserService;


@SessionAttributes("name")
@Controller
public class MainController{
	@Autowired
	private FieldService fieldService;
	@Autowired
	private UserService userService;
	@Autowired
	PollService pollService;
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
	
	/*
	 * @GetMapping(value = "/get-responses-page", produces = "application/json")
	 * public @ResponseBody Pageable getResponsesPage(@RequestParam("page") int
	 * page, @RequestParam("size") int size) { if(page >
	 * responseService.getTotalPages(size)) { //EXCEPTION; } return
	 * response.getPage(page , size); }
	 */
	
	
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
	
	@GetMapping(value = "get-fields-page", params = {"page", "size"})
	public @ResponseBody Page<FieldDto> getPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size){
		Page<Field> entities = fieldService.findAll(PageRequest.of(page, size));
		Page<FieldDto> dtos = entities.map(getConverter());
		return dtos;
	}
	
	
	@GetMapping(value = "/get-all-responses", produces = "application/json")
	public @ResponseBody List<PollDto> getAllResponses(){
		List<Poll> entities = pollService.findAllPolls();
		List<PollDto> dtos = new ArrayList<PollDto>();
		for(Poll r: entities) {
			PollDto dto = new PollDto();
		    dto.setId(r.getId());
		    dto.setUserName(r.getUser().getFirstName() + " " + r.getUser().getLastName());
		    dto.toString();
		    List<ResponseDto> responses = new ArrayList<ResponseDto>();
		    for(Response response : r.getResponses()) {
		    	ResponseDto responseDto = new ResponseDto(response.getId(), response.getField().getId(), response.getValue());
                responses.add(responseDto);
		    }
		    dto.setResponses(responses);
		    dtos.add(dto);
		}
		return dtos;
	}
	
	@GetMapping(value = "/get-personal-responses", produces = "application/json")
	public @ResponseBody List<PollDto> getPersonalResponses(Principal principal){
		List<Poll> entities = pollService.findPollsByEmail(principal.getName());
		List<PollDto> dtos = new ArrayList<PollDto>();
		for(Poll r: entities) {
			PollDto dto = new PollDto();
		    dto.setId(r.getId());
		    dto.setUserName(r.getUser().getFirstName() + " " + r.getUser().getLastName());
		    dto.toString();
		    List<ResponseDto> responses = new ArrayList<ResponseDto>();
		    for(Response response : r.getResponses()) {
		    	ResponseDto responseDto = new ResponseDto(response.getId(), response.getField().getId(), response.getValue());
                responses.add(responseDto);
		    }
		    dto.setResponses(responses);
		    dtos.add(dto);
		}
		return dtos;
	}
	
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
				if(!fieldDto.getOptions().equals("")) {
					String opts = fieldDto.getOptions();
					String[] options = opts.split("\n");
					Option option;
					field.setOptions(new ArrayList<Option>());
					for(int i = 0; i < options.length; i++) {/*
						optionId = new OptionId(i, fieldDto.getLabel());*/
						option = new Option( i/*optionId*/, options[i].trim(), field);
						field.addOption(option);
					}
				}
				fieldService.createField(field);
				message.setMessage(String.valueOf((field.getId())));		             
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
		fieldService.deleteField(Long.parseLong(label.getMessage()));
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

	@MessageMapping("/responses")
	@SendTo("/topic/responses")
	public List<ResponseDto> getResponse(List<ResponseDto> responses, Principal principal) {
		if(!responses.isEmpty()) {
		    ModelMapper mapper = new ModelMapper();
		    Poll poll = new Poll();
		    Response entity;
		    List<ResponseDto> elementsToRemove = new ArrayList<ResponseDto>();
		    List<Response> elementsToSave = new ArrayList<Response>();
		    for(ResponseDto dto : responses) {
			    if(!dto.getValue().equals("")) {
				    entity = mapper.map(dto, Response.class);
			        entity.setField(fieldService.findById(dto.getFieldId()));
			        entity.setPoll(poll);
			        entity.setValue(dto.getValue().replace("\n", "\\n"));
			        elementsToSave.add(entity);
			    } else {
			    	elementsToRemove.add(dto);
			    }
		    }
		    poll.setUser(userService.findByEmail(principal.getName()));
		    poll.setResponses(elementsToSave);
		    pollService.savePoll(poll);
		    responses.removeAll(elementsToRemove);
		}
		return responses;
	}
	
	
	@RequestMapping("/responses")
	public ModelAndView responses(){
		ModelAndView model = new ModelAndView("responses");
		return model;
	}
	@RequestMapping("/my-responses")
	public ModelAndView myResponses(){
		ModelAndView model = new ModelAndView("myResponses");
		return model;
	}
	
	@GetMapping("/success")
	public ModelAndView success(){
		ModelAndView model = new ModelAndView("success");
		return model;
	}
	
	public void sendResponse(List<ResponseDto> responses){
		template.convertAndSend("/topic/responses", responses);
	}
	
	@ModelAttribute("name")
	public String setUpUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.findByEmail(email);
		return user.getFirstName() + " " + user.getLastName();
	}
	
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
