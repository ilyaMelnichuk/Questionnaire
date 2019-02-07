package com.example.questionnaire.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.questionnaire.dto.ResponseDto;
import com.example.questionnaire.service.FieldService;

public class RequiredConstraintValidator  implements ConstraintValidator<Required, ResponseDto> {
	 
	 @Autowired
	 private FieldService fieldService;
	 
	 @Override
	 public void initialize(Required constraint) {
	     
	 }

	 @Override
	 public boolean isValid(ResponseDto responseDto, ConstraintValidatorContext context){
		 if(fieldService.findById(responseDto.getFieldId()).isRequired() && responseDto.getValue() == "") {
			 return false;
		 }
	    return true;
	 }
}
