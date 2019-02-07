package com.example.questionnaire.dto;

import com.example.questionnaire.validation.Required;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Required
public class ResponseDto {

	private long id;
	
	private Long fieldId;
	
	private String value;
	
	private String user;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ResponseDto() {
		
	}
	
	@JsonCreator
	public ResponseDto(@JsonProperty("id") long id, @JsonProperty("fieldId") Long fieldId, @JsonProperty("value") String value) {
		super();
		this.id = id;
		this.fieldId = fieldId;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
