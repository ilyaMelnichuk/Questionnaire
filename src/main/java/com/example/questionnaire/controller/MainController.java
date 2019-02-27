package com.example.questionnaire.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
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
import com.example.questionnaire.dto.PollTemplateDto;
import com.example.questionnaire.dto.ResponseDto;
import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.Poll;
import com.example.questionnaire.entity.PollTemplate;
import com.example.questionnaire.entity.Response;
import com.example.questionnaire.entity.Type;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.FieldService;
import com.example.questionnaire.service.PollService;
import com.example.questionnaire.service.PollTemplateService;
import com.example.questionnaire.service.UserService;


@SessionAttributes("name")
@Controller
public class MainController{
	@Autowired
	private FieldService fieldService;
	@Autowired
	private UserService userService;
	@Autowired
	private PollService pollService;
	@Autowired
	private PollTemplateService pollTemplateService;
	@Autowired
	private SimpMessagingTemplate template;
	@RequestMapping(value = "/")
	public ModelAndView home() {
		return new ModelAndView("main");
	}

	@GetMapping("/fields")
	public ModelAndView getAllFields(HttpServletRequest request){
		ModelAndView model = new ModelAndView("main");
		return model;
	}
	
	@GetMapping("/templates")
	public ModelAndView getAllTemplates(HttpServletRequest request){
		ModelAndView model = new ModelAndView("main");
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

		Page<FieldDto> dtos = entities.map(getFieldConverter());
		return dtos;
	}

	@GetMapping(value = "get-fields-page")
	public @ResponseBody Page<FieldDto> getFieldsPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size){
		Page<Field> entities = fieldService.findAll(PageRequest.of(page, size, Sort.by("id").ascending()));
		Page<FieldDto> dtos = entities.map(getFieldConverter());
		return dtos;
	}

	@GetMapping(value = "get-polls-page")
	public @ResponseBody Page<PollDto> getPollsPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size){
		Page<Poll> entities = pollService.findAll(PageRequest.of(page, size));
		Page<PollDto> dtos = entities.map(getPollConverter());
		return dtos;
	}
	
	@GetMapping(value = "get-templates-page")
	public @ResponseBody Page<PollTemplateDto> getTemplatesPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size){
		Page<PollTemplate> entities = pollTemplateService.findAll(PageRequest.of(page, size, Sort.by("id").ascending()));
		Page<PollTemplateDto> dtos = entities.map(getTemplateConverter());
		return dtos;
	}

	@GetMapping(value = "get-personal-polls-page") 
	public @ResponseBody Page<PollDto> getPersonalPollsPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size, Principal principal){ 
		String email = principal.getName(); 
		Page<Poll> entities = pollService.findByEmail(email, PageRequest.of(page, size)); 
		Page<PollDto> dtos = entities.map(getPollConverter());
		return dtos; 
	}

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
				field.setLabel(replaceSpecialCharacters(field.getLabel()));
				if(!fieldDto.getOptions().equals("")) {
					String opts = fieldDto.getOptions();
					String[] options = opts.split("\n");
					Option option;
					field.setOptions(new TreeSet<Option>());
					for(int i = 0; i < options.length; i++) {
						option = new Option( i, replaceSpecialCharacters(options[i].trim()), field);
						field.addOption(option);
					}
				}
				if(field.getId() != null) {
					fieldService.updateField(field);
				}else {
					fieldService.createField(field);	
				}
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

	@SubscribeMapping("/user/topic/my-responses")
	@MessageMapping("/responses")
	@SendTo("/topic/responses")
	public PollDto getResponse(List<ResponseDto> responses, Principal principal) {
		Poll poll = new Poll();
		if(!responses.isEmpty()) {
			ModelMapper mapper = getPollToPollDtoMapper();
			Response entity;
			List<ResponseDto> elementsToRemove = new ArrayList<ResponseDto>();
			poll.setUser(userService.findByEmail(principal.getName()));
			poll = pollService.savePoll(poll);
			for(ResponseDto dto : responses) {
				if(!dto.getValue().equals("")) {
					entity = mapper.map(dto, Response.class);
					//entity.setPollField(PollService.findById(dto.getPollFieldId()));
					entity.setValue(replaceSpecialCharacters(dto.getValue()));
					poll.addResponse(entity);
				} else {
					elementsToRemove.add(dto);
				}
			}
			poll = pollService.savePoll(poll);
			responses.removeAll(elementsToRemove);
			PollDto pollDto = mapper.map(poll, PollDto.class);
			
			template.convertAndSendToUser(principal.getName(), "/topic/my-responses", pollDto);
			return pollDto;
		}else {
			PollDto pollDto = new PollDto(-1, "", new ArrayList<ResponseDto>());
			return pollDto;	
		}
	}


	@RequestMapping("/responses")
	public ModelAndView responses(){
		ModelAndView model = new ModelAndView("main");
		return model;
	}
	@RequestMapping("/my-responses")
	public ModelAndView myResponses(){
		ModelAndView model = new ModelAndView("main");
		return model;
	}

	@GetMapping("/success")
	public ModelAndView success(){
		ModelAndView model = new ModelAndView("main");
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

	private Function<Field, FieldDto> getFieldConverter(){
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
	private Function<Poll, PollDto> getPollConverter(){
		return new Function<Poll, PollDto>() {

			@Override
			public PollDto apply(Poll entity) {
				PollDto dto = new PollDto();
				ModelMapper mapper = getPollToPollDtoMapper();
				dto = mapper.map(entity, PollDto.class);
				return dto;
			}
		};
	}
	
	private Function<PollTemplate, PollTemplateDto> getTemplateConverter() {
		return new Function<PollTemplate, PollTemplateDto>() {

			@Override
			public PollTemplateDto apply(PollTemplate entity) {
				PollTemplateDto dto = new PollTemplateDto();
				ModelMapper mapper = getPollTemplateToPollTemplateDtoMapper();
				dto = mapper.map(entity, PollTemplateDto.class);
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
	private ModelMapper getPollToPollDtoMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<Poll, PollDto>() {
			@Override
			protected void configure() {
				map().setUserName(source.convertUserToUserName());
				map().setResponses(source.convertResponsesToResponsesDto());
			}
		});
		return mapper;
	}
	
	private ModelMapper getPollTemplateToPollTemplateDtoMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<PollTemplate, PollTemplateDto>() {
			@Override
			protected void configure() {
				map().setTotalFields(source.getTotalFields());
				map().setTotalResponses(source.getTotalResponses());
				map().setFields(null);
			}
		});
		return mapper;
	}
	
	public String replaceSpecialCharacters(String string) {
		return string.replace("\"", "\\\"").replace("\'", "\\\'")
				.replace("<", "&lt").replace(">", "&gt").replace("\n", "\\n");
	}
}
