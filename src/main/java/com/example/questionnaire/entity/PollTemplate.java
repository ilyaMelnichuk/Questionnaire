package com.example.questionnaire.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="poll_template", schema="data")
public class PollTemplate {
	@Id
	@Column(name = "id")
	@SequenceGenerator(name="poll_template_id_seq", schema = "data", sequenceName = "poll_template_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_template_id_seq")
    private Long id;
	
	@Column(name = "is_active")
	private boolean isActive;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "poll_template_poll", schema = "data", joinColumns = @JoinColumn(name = "poll_template_id"), inverseJoinColumns = @JoinColumn(name = "field_id"))
    private Set<Field> fields = new HashSet<Field>();
    
    @OneToMany(mappedBy = "pollTemplate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private Set<Poll> polls = new HashSet<Poll>();

    public PollTemplate() {
    	
    }
    
	public PollTemplate(Long id, boolean isActive, Set<Field> fields, Set<Poll> polls) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.fields = fields;
		this.polls = polls;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isisActive() {
		return isActive;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}

	public Set<Poll> getPolls() {
		return polls;
	}

	public void setPolls(SortedSet<Poll> polls) {
		this.polls = polls;
	}
}
