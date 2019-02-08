package com.example.questionnaire.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="poll", schema = "data")
public class Poll {
	@Id
	@Column(name = "id")
	private Long id;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "email")
	private User user;
	
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Response> responses;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Response> getResponses() {
		return responses;
	}
	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
}
