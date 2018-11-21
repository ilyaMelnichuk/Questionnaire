package com.example.questionnaire.entity;

public enum Type {
	SINGLE_LINE_TEXT("Single line text"),
	MULTILINE_TEXT("Multiline text"),
	RADIO_BUTTON("Radio button"),
	CHECKBOX("Checkbox"),
	COMBOBOX("Combobox"),
	DATE("Date");
	
	private String name; 
	
	Type(String name){
		this.name = name;
	}
	
	public String getName() {
		return this.name();
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}
