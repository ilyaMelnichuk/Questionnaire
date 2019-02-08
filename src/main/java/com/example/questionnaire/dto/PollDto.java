package com.example.questionnaire.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PollDto {
	private long id;
	private String userName;
	private List<ResponseDto> responses;
	
	public PollDto() {
		
	}
	
	@Override
	public String toString() {
		return "PollDto [id=" + id + ", userName=" + userName + ", responses=" + responses + "]";
	}

	@JsonCreator
	public PollDto(@JsonProperty("id") long id, @JsonProperty("userName") String userName, @JsonProperty("responses") List<ResponseDto> responses) {
		super();
		this.id = id;
		this.userName = userName;
		this.responses = responses;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<ResponseDto> getResponses() {
		return responses;
	}
	public void setResponses(List<ResponseDto> responses) {
		this.responses = responses;
	}
}
