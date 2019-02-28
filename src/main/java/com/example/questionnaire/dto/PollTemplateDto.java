package com.example.questionnaire.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PollTemplateDto {
	private long id;
	private String name;
	private boolean isActive;
	private long totalFields;
	private long totalResponses;
	private List<Long> fieldsToDelete;
	private List<Long> fieldsToAdd;

	public PollTemplateDto() {

	}

	@JsonCreator
	public PollTemplateDto(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("isActive") boolean isActive, @JsonProperty("totalFields") long totalFields, @JsonProperty("totalResponses") long totalResponses) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.totalFields = totalFields;
        this.setTotalResponses(totalResponses);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(long totalFields) {
		this.totalFields = totalFields;
	}

	public long getTotalResponses() {
		return totalResponses;
	}

	public void setTotalResponses(long totalResponses) {
		this.totalResponses = totalResponses;
	}

	public boolean isisActive() {
		return isActive;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Long> getFieldsToDelete() {
		return fieldsToDelete;
	}

	public void setFieldsToDelete(List<Long> fieldsToDelete) {
		this.fieldsToDelete = fieldsToDelete;
	}

	public List<Long> getFieldsToAdd() {
		return fieldsToAdd;
	}

	public void setFieldsToAdd(List<Long> fieldsToAdd) {
		this.fieldsToAdd = fieldsToAdd;
	}

}
