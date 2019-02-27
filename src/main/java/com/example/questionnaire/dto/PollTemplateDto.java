package com.example.questionnaire.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PollTemplateDto {
	private long id;
	private String name;
	private long totalFields;
	private long totalResponses;
	private List<Long> fields;
	private long fieldToDelete;
	private long fieldToAdd;

	public PollTemplateDto() {

	}

	@JsonCreator
	public PollTemplateDto(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("totalFields") long totalFields, @JsonProperty("totalResponses") long totalResponses) {
		super();
		this.id = id;
		this.name = name;
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

	public List<Long> getFields() {
		return fields;
	}

	public void setFields(List<Long> fields) {
		this.fields = fields;
	}

	public long getFieldToDelete() {
		return fieldToDelete;
	}

	public void setFieldToDelete(long fieldToDelete) {
		this.fieldToDelete = fieldToDelete;
	}

	public long getFieldToAdd() {
		return fieldToAdd;
	}

	public void setFieldToAdd(long fieldToAdd) {
		this.fieldToAdd = fieldToAdd;
	}

	public long getTotalResponses() {
		return totalResponses;
	}

	public void setTotalResponses(long totalResponses) {
		this.totalResponses = totalResponses;
	}

}
