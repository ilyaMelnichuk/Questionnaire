package com.example.questionnaire.entity;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "poll_field", schema = "data")
public class PollField {

	@Id 
	@Column(name = "id") 
	@SequenceGenerator(name="poll_field_id_seq", schema = "data",
    sequenceName="poll_field_id_seq",
    allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator="poll_field_id_seq")
	private Long id;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//edited
	@Column(name = "label")
	private String label;

	@Column(name = "isactive")
	private boolean isActive;

	@Column(name = "required")
	private boolean required;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Type type;

	@MapsId("OptionId")
	@OneToMany(mappedBy = "pollField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("id ASC")
	private SortedSet<PollOption> pollOptions = new TreeSet<PollOption>();
	
	@ManyToOne
	private Poll poll;
	
	@OneToOne(optional = false, mappedBy = "pollField")
	private Response response;

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public SortedSet<PollOption> getPollOptions() {
		return pollOptions;
	}

	public void setOptions(SortedSet<PollOption> pollOptions) {
		this.pollOptions = pollOptions;
	}

	public void addOption(PollOption pollOption) {
		this.pollOptions.add(pollOption);
	}

	@Override
	public String toString() {
		return "Field [label=" + label + ", isActive=" + isActive + ", required=" + required + ", type=" + type
				+ ", options=" + pollOptions + "]";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isisActive() {
		return isActive;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String convertOptionsToStringNL() {
		String result = "";
		if (this.pollOptions == null || this.pollOptions.isEmpty()) {
			result = null;
		} else {
			for (PollOption pollOption : this.pollOptions) {
				result += pollOption.getValue() + "\n";
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public String convertEnumToString() {
		return type.getText();
	}

	public String convertOptionsToStringPipe() {
		String result = "";
		if (this.pollOptions == null || this.pollOptions.isEmpty()) {
			result = null;
		} else {
			for (PollOption option : this.pollOptions) {
				result += option.getValue() + "|";
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
