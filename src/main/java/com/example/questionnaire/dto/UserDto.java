package com.example.questionnaire.dto;

import javax.validation.constraints.*;

public class UserDto {
	
	public interface New{
		
	}
	
	public interface Exist{
		
	}
	
	public interface EditProfile extends Exist{
		
	}
    
	public interface ChangePassword extends Exist{
		
	}
	
	public interface ResetPassword extends Exist{
		
	}
	
	@NotNull(groups = {New.class, EditProfile.class})
	@Email(groups = {New.class, EditProfile.class})
	private String email;
	
	
	@NotNull(groups = {New.class})
	@Null(groups = {EditProfile.class, ResetPassword.class})
	@Size(min = 6, message = "password length should be 6 characters at least", groups = {New.class})
	private String password;
	
	@NotNull(groups = {ResetPassword.class})
	@Null(groups = {New.class, EditProfile.class})
	@Size(min = 6, message = "password length should be at least 6 characters", groups = {ChangePassword.class, ResetPassword.class})
	private String newPassword;
	
	
	@NotNull(groups = {New.class, EditProfile.class})
	@Null(groups = {ChangePassword.class, ResetPassword.class})
	private String firstName;
	
	
	@NotNull(groups = {New.class, EditProfile.class})
	@Null(groups = {ChangePassword.class, ResetPassword.class})
	private String lastName;
	
	
	@NotNull(groups = {New.class, EditProfile.class})
	@Null(groups = {ChangePassword.class, ResetPassword.class})
	private String phoneNumber;
	
	public UserDto() {
	}
	
	public UserDto(String email, String firstName, String lastName, String phoneNumber) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
}
