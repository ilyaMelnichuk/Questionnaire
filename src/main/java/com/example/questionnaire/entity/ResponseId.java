package com.example.questionnaire.entity;

import java.io.Serializable;

public class ResponseId implements Serializable{
    private long id;
    private Long field;
    public ResponseId() {
	}
    
	public ResponseId(long id, Long field) {
		super();
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getField() {
		return field;
	}
	public void setField(Long field) {
		this.field = field;
	}
}
