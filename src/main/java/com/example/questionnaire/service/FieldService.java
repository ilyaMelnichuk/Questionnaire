package com.example.questionnaire.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.FieldRepository;
import com.example.questionnaire.dao.OptionRepository;
import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.OptionId;



@Service
public class FieldService {
	@Autowired
	private FieldRepository fieldRepository;
	@Autowired
	private OptionRepository optionRepository;
	
	public Page<Field> findAll(PageRequest pageRequest){
		return fieldRepository.findAll(pageRequest);
	}
	
	public List<Field> findAllActive(){
		List<Field> result = new ArrayList<Field>();
		Iterable<Field> f = fieldRepository.findByIsActive(true);
		for(Field field : f) {
			result.add(field);
		}
		return result;
	}
	
	public void updateField(String oldLabel, Field field) {
		fieldRepository.updateByLabel(oldLabel, field.getLabel(), field.getType(), field.isRequired(), field.isisActive());
		List<Option> options = field.getOptions();
		if((field.getType().name() ==  "COMBOBOX" || field.getType().name() ==  "RADIO_BUTTON" || field.getType().name() ==  "CHECKBOX")&& options != null) {
			int counter = 0;
			for(Option option : options) {
			    optionRepository.save(option);
			    counter++;
		    }
			if(optionRepository.existsById(new OptionId(counter, field.getLabel()))) {
				optionRepository.deleteWhereIdMoreThan(counter - 1, field);
			}
		}else {
			if(optionRepository.existsByFieldLabel(field.getLabel())) {
				optionRepository.deleteByFieldLabel(field);	
			}
		}
	}
	
	public boolean exists(Field field) {
		return fieldRepository.existsByLabel(field.getLabel());
	}
	
	public void createField(Field field) {
		fieldRepository.save(field);
	}
	public void deleteField(String label) {
		fieldRepository.deleteById(label);
	}
	public Field findByLabel(String label) {
		return fieldRepository.findByLabel(label);
	}
}
