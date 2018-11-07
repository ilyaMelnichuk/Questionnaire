package com.example.questionnaire.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fields" , schema = "schema")
public class Field {
    @Override
	public String toString() {
		return "Field [id=" + id + ", isActive=" + isActive + ", label=" + label + ", required=" + required + ", type="
				+ type + "]";
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "isactive")
    private boolean isActive;
    @Column(name = "label")
    private String label;
    @Column(name = "required")
    private boolean required;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
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
}
