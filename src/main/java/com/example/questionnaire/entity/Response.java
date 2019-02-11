package com.example.questionnaire.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "response", schema = "data")
//@IdClass(ResponseId.class)
public class Response {
	
	@Id
	@Column(name="id")
	@SequenceGenerator(name = "response_id_seq", schema = "data", sequenceName = "response_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "response_id_seq", strategy = GenerationType.SEQUENCE)
    private long id;
	
	@ManyToOne
	private Field field;
	
	
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "poll_id", insertable = true, updatable = true)
	private Poll poll;
	
	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	@Column(name="value")
	private String value;
	
	@Override
	public String toString() {
		return "Response [id=" + id + ", field=" + field.getLabel() + ", value=" + value + "]";
	}

	public Response() {
	}
	
	public Response(long id, String value, Field field, Poll poll) {
		this.id = id;
		this.value = value;
		this.field = field;
		this.poll = poll;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
