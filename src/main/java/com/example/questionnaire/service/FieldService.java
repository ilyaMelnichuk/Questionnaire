package com.example.questionnaire.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.FieldRepository;
import com.example.questionnaire.model.Field;



@Service
public class FieldService {
	@Autowired
	private FieldRepository fieldRepository;
	
	public List<Field> findAllFields(){
		List<Field> fields = new ArrayList<Field>();
		Iterable<Field> f = fieldRepository.findAllFields("text");
		for(Field field : f) {
			fields.add(field);
		}
		return fields;
	}
}
