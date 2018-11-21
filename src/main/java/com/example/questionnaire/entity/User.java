package com.example.questionnaire.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user", schema="security")
public class User implements UserDetails{
	@Id
    @Column(name = "email")
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email")
    private String email;
    @Column(name = "password")
    @Length(min = 6, max = 255, message = "Password should have at least 6 and no more than 255 characters")
    private String password;
	@Column(name = "first_name")
	@Length(max = 255, message = "Your first name should have no more than 255 characters")
    private String firstName;
    @Column(name = "last_name")
    @Length(max = 255, message = "Your last name should have no more than 255 characters")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", schema = "security", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_name"))
    private Set<Role> roles;
    
    
	public String getEmail() {
		return email;
	}
	
	@JsonIgnore
	public Set<Role> getRoles() {
		return roles;
	}
	
	@JsonIgnore
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@JsonIgnore
	@Override
	public String getUsername() {
		return email;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
