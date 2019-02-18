package com.example.questionnaire.service;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.PasswordResetTokenRepository;
import com.example.questionnaire.entity.PasswordResetToken;
import com.example.questionnaire.entity.User;

@Service
public class SecurityService {
	@Autowired 
	private PasswordResetTokenRepository passwordResetTokenRepository;

	public void createPasswordResetToken(User user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(user, token, new Date(System.currentTimeMillis() + PasswordResetToken.getExpirationTime()));
		passwordResetTokenRepository.save(passwordResetToken);
	}

	public String validatePasswordResetToken(String mail, String token){
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
		if(passwordResetToken == null || !passwordResetToken.getUser().getEmail().equals(mail)) {
			return "token is not valid!";
		}
		Date date = new Date();
		if ((passwordResetToken.getExpirationDate().getTime() - date.getTime()) <= 0) {
			return "expired";
		}

		User user = passwordResetToken.getUser();
		Authentication auth = new UsernamePasswordAuthenticationToken(
				user, null, Arrays.asList(
						new SimpleGrantedAuthority("ROLE_CHANGE_PASSWORD")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		passwordResetTokenRepository.delete(passwordResetToken);
		return null;
	}

	public String getApplicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
