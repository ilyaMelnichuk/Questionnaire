package com.example.questionnaire.entity;

import java.io.Serializable;

public class ResponseId implements Serializable{
    private long id;
    private String field;
    
    public ResponseId() {
	}
    
	public ResponseId(long id, String field) {
		super();
		this.id = id;
		this.field = field;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
}
