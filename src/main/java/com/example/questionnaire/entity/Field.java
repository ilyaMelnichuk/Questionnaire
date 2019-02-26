package com.example.questionnaire.entity;

import java.util.List;
import java.util.Set;
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
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "field", schema = "data")
public class Field {

	@Id 
	@Column(name = "id") 
	@SequenceGenerator(name="field_id_seq", schema = "data",
    sequenceName="field_id_seq",
    allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator="field_id_seq")
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
	@OneToMany(mappedBy = "field", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("id ASC")
	private SortedSet<Option> options = new TreeSet<Option>();
	
	@ManyToMany(mappedBy = "fields")
	private Set<PollTemplate> pollTemplate;

	public SortedSet<Option> getOptions() {
		return options;
	}

	public void setOptions(SortedSet<Option> options) {
		this.options = options;
	}

	public void addOption(Option option) {
		this.options.add(option);
	}

	@Override
	public String toString() {
		return "Field [label=" + label + ", isActive=" + isActive + ", required=" + required + ", type=" + type
				+ ", options=" + options + "]";
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
		if (this.options == null || this.options.isEmpty()) {
			result = null;
		} else {
			for (Option option : this.options) {
				result += option.getValue() + "\n";
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
		if (this.options == null || this.options.isEmpty()) {
			result = null;
		} else {
			for (Option option : this.options) {
				result += option.getValue() + "|";
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
