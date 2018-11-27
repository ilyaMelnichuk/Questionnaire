package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "option", schema = "data")
@IdClass(OptionId.class)
public class Option {
	@Id
	@Column(name = "id")
    private long id;
	@ManyToOne
    @Id
    private Field field;
    @Column(name = "value")
    private String value;
    
    
    public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Option() {
	}
	public Option(long id, String value, Field field) {
		super();
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
