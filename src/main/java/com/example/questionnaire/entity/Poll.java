package com.example.questionnaire.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.questionnaire.dto.ResponseDto;

@Entity
@Table(name="poll", schema = "data")
public class Poll {
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "poll_id_seq", schema = "data", sequenceName = "poll_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "poll_id_seq", strategy = GenerationType.SEQUENCE)
	private Long id;	
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "email")
	private User user;
	
	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Response> responses = new ArrayList<Response>();
	
	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PollField> pollFields = new ArrayList<PollField>();
	
	@ManyToOne
	private PollTemplate pollTemplate;
	
	public PollTemplate getPollTemplate() {
		return pollTemplate;
	}

	public void setPollTemplate(PollTemplate pollTemplate) {
		this.pollTemplate = pollTemplate;
	}

	public void addResponse(Response response) {
		responses.add(response);
		response.setPoll(this);
	}
	
	public void removeResponse(Response response) {
		responses.remove(response);
		response.setPoll(null);
	}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	
	public String convertUserToUserName() {
		return user.getFirstName() + " " + user.getLastName();
	}
	public List<ResponseDto> convertResponsesToResponsesDto() {
		List<ResponseDto> responsesDto = new ArrayList<ResponseDto>();
	    for(Response response : getResponses()) {
	    	ResponseDto responseDto = new ResponseDto(response.getId(), response.getPollField().getId(), response.getValue());
            responsesDto.add(responseDto);
	    }
	    return responsesDto;
	}
}
