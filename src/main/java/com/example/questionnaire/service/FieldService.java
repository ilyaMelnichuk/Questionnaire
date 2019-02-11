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
	
	public void updateField(Field field) {
		fieldRepository.save(field);
		/*
		 * fieldRepository.updateById(field.getId(),field.getLabel(), field.getType(),
		 * field.isRequired(), field.isisActive());
		 */
		List<Option> options = field.getOptions();
		if((field.getType().name() ==  "COMBOBOX" || field.getType().name() ==  "RADIO_BUTTON" || field.getType().name() ==  "CHECKBOX")&& options != null) {
			long counter = 0;
			for(Option option : options) {
			    optionRepository.save(option);
			    counter++;
		    }
			if(optionRepository.existsById(new OptionId(counter, field.getId()))) {
				optionRepository.deleteWhereIdMoreThan(counter - 1, field);
			}
		}else {
			if(optionRepository.existsByFieldId(field.getId())) {
				optionRepository.deleteByFieldId(field);	
			}
		}
	}
	
	/*
	 * public void updateField(Field field) {
	 * fieldRepository.saveField(field); 
	 * if((field.getType().name() == "COMBOBOX" ||
	 * field.getType().name() == "RADIO_BUTTON" || field.getType().name() ==
	 * "CHECKBOX")&& options != null) { int counter = 0; for(Option option :
	 * options) { optionRepository.save(option); counter++; }
	 * if(optionRepository.existsById(new OptionId(counter, field.getId()))) {
	 * optionRepository.deleteWhereIdMoreThan(counter - 1, field); } }else {
	 * if(optionRepository.existsByFieldId(field.getId())) {
	 * optionRepository.deleteByFieldId(field); } } }
	 */
	
	public boolean exists(Field field) {
		return fieldRepository.existsById(field.getLabel());
	}
	
	/*
	 * public boolean exists(Field field) { return
	 * fieldRepository.existsById(field.getId()); }
	 */
	
	public void createField(Field field) {
		fieldRepository.save(field);
	}

	/*
	 * public void saveField(Field field) { fieldRepository.save(field); }
	 */
	public void deleteField(Long id) {
		fieldRepository.deleteById(id);
	}
	public Field findById(Long id) {
		return fieldRepository.findById(id);
	}
	
	/*
	 * public void deleteField(long Id) { fieldRepository.deleteById(id); }
	 * public Field findById(long id) { return
	 * fieldRepository.findById(id); }
	 */
}
