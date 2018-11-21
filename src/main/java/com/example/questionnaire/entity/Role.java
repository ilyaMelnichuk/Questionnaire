package com.example.questionnaire.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role", schema="security")
public class Role implements GrantedAuthority{
	@Id
	@Column(name = "role_name")
	private String roleName;
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Override
	public String getAuthority() {
		return roleName;
	}
	
    @Override
	public String toString() {
		return roleName;
	}
}
    
