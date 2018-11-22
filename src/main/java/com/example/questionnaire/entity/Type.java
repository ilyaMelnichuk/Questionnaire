package com.example.questionnaire.entity;

public enum Type {
	SINGLE_LINE_TEXT("Single line text"),
	MULTILINE_TEXT("Multiline text"),
	RADIO_BUTTON("Radio button"),
	CHECKBOX("Checkbox"),
	COMBOBOX("Combobox"),
	DATE("Date");
	
	private String text; 
	
	Type(String text){
		this.text= text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
