package com.example.questionnaire.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

public class OptionId implements Serializable{
    private long id;
    private String field;
    
    public OptionId() {
	}
    
	public OptionId(long id, String field) {
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
		OptionId other = (OptionId) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
    
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;*/
}
