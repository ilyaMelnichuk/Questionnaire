package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "poll_option", schema = "data")
@IdClass(PollOptionId.class)
public class PollOption implements Comparable<PollOption>{
	@Id
	@Column(name = "id")
    private long id;
	@ManyToOne
    @Id
    private PollField pollField;
    @Column(name = "value")
    private String value;
    
    
    public PollField getPollField() {
		return pollField;
	}
	public void setPollField(PollField pollField) {
		this.pollField = pollField;
	}
	public PollOption() {
	}
	public PollOption(long id, String value, PollField pollField) {
		super();
		this.id = id;
		this.value = value;
		this.pollField = pollField;
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
	@Override
	public int compareTo(PollOption option) {
		if(option.getId() > id) {
			return -1;	
		}else{
			return 1;
		}
	}
}
