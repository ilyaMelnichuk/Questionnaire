package com.example.questionnaire.entity;

import java.io.Serializable;

public class PollOptionId implements Serializable{
    private Long id;
    private Long pollField;
    
    public PollOptionId() {
	}
    
	public PollOptionId(long id, Long pollField) {
		this.id = id;
		this.pollField = pollField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPollField() {
		return pollField;
	}

	public void setPollField(Long pollField) {
		this.pollField = pollField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (pollField ^ (pollField >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollOptionId other = (PollOptionId) obj;
		if (pollField != other.pollField)
			return false;
		return true;
	}
}
