package com.example.questionnaire.entity;

import java.io.Serializable;

public class ResponseId implements Serializable{
    private long id;
    private String field;
    
    public ResponseId() {
	}
    
	public ResponseId(long id, String field) {
		super();
		this.id = id;
		this.field = field;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		ResponseId other = (ResponseId) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
