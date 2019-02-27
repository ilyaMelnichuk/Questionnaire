package com.example.questionnaire.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "response", schema = "data")
public class Response {
	
	@Id
	@Column(name="id")
	@SequenceGenerator(name = "response_id_seq", schema = "data", sequenceName = "response_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "response_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "poll_field_id")
	private PollField pollField;
	
    @ManyToOne(fetch = FetchType.LAZY)
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
		return "Response [id=" + id + ", field=" + pollField.getLabel() + ", value=" + value + "]";
	}

	public Response() {
	}
	
	public Response(Long id, String value, PollField pollField, Poll poll) {
		this.id = id;
		this.value = value;
		this.pollField = pollField;
		this.poll = poll;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PollField getPollField() {
		return pollField;
	}
	public void setPollField(PollField pollField) {
		this.pollField = pollField;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
