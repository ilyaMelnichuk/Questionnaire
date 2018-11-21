package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "response", schema = "data")
public class Response {
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Id
    @Column(name = "id")    //1; 3; 528
    private long id;
    @Column(name = "field") //first_name; last_name; mobile
    private String text;
    @Column(name = "value") //'john'; 'doe'; '+341241234325fff':  add field -> (add record{id, field{field_id}, {n/a}),
    private String value;   //update field -> (delete records{where field = field_id} add record{id, field{field_id}, {n/a}),
                            //delete field -> (delete records{where field = field_id})
}
