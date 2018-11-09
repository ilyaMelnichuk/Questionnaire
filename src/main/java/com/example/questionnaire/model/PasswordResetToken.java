package com.example.questionnaire.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "token", schema = "security")
public class PasswordResetToken {
    private static final int EXPIRATION_TIME = 60*60*1000;


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "email")
    private User user;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "expiration_date")
    private Date expirationDate;
    
    public PasswordResetToken() {
    }
    
    public PasswordResetToken(User user, String token, Date expirationDate) {
    	this.user = user;
    	this.token = token;
    	this.expirationDate = expirationDate;
    }
    
    public static int getExpirationTime() {
		return EXPIRATION_TIME;
	}
    
	public Date getExpirationDate() {
		return expirationDate;
	}


	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getToken() {
		return token;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setToken(String token) {
		this.token = token;
	}    
}
