package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "response", schema = "data")
@IdClass(ResponseId.class)
public class Response {
	
	@Id
	@Column(name="id")
    private long id;
	
	@ManyToOne
	@Id
	private Field field;
	
	@Column(name="value")
	private String value;
	
	@Override
	public String toString() {
		return "Response [id=" + id + ", field=" + field.getLabel() + ", value=" + value + "]";
	}

	public Response() {
	}
	
	public Response(long id, String value, Field field) {
		this.id = id;
		this.value = value;
		this.field = field;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
