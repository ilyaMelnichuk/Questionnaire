package com.example.questionnaire.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

public class OptionId implements Serializable{
    private Long id;
    private Long field;
    
    public OptionId() {
	}
    
	public OptionId(long id, Long field) {
		this.id = id;
		this.field = field;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getField() {
		return field;
	}

	public void setField(Long field) {
		this.field = field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (field ^ (field >>> 32));
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
		if (field != other.field)
			return false;
		return true;
	}

	/*
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((field == null) ? 0 : field.hashCode()); result =
	 * prime * result + (int) (id ^ (id >>> 32)); return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; OptionId other = (OptionId) obj; if (field == other.field) return
	 * false; if (id != other.id) return false; return true; }
	 */
	
	
    
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;*/
}
