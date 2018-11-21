package com.example.questionnaire.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.*;

import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.OptionId;

public class FieldDto {
	public interface Save{}
	@NotBlank(groups = {Save.class}, message = "label shouldn't be empty")
    private String label;
    private String oldLabel;
    private String type;
    private boolean required;
    private boolean isActive;
    private String options;
    
    
	@Override
	public String toString() {
		return "FieldDto [label=" + label + ", oldLabel=" + oldLabel + ", type=" + type + ", required=" + required
				+ ", isActive=" + isActive + ", options=" + options + "]";
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getOldLabel() {
		return oldLabel;
	}
	public void setOldLabel(String oldLabel) {
		this.oldLabel = oldLabel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isisActive() {
		return isActive;
	}
	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public List<Option> convertStringToOptions(Field field) {
		List<Option> result = new ArrayList<Option>();
		OptionId optionId;
		Option option;
		String[] options = this.options.split("\n");
		for(int i = 0; i < options.length; i++) {
			option = new Option(i, options[i].trim(), field);
		}
		return result;
	}
}
