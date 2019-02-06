package com.example.questionnaire.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		    String redirectUrl = "/login";
		    boolean isUser = false;
		    boolean isAdmin = true;
		    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		    for(GrantedAuthority authority: authorities) {
		    	if(authority.getAuthority().equals("ROLE_USER")) {
		    		isUser = true;
		    	}else if(authority.getAuthority().equals("ROLE_ADMIN")) {
		    		isAdmin = true;
		    	}
		    }
		    if(isUser == true) {
		    	redirectUrl = "/";
		    }else if(isAdmin == true) {
		    	redirectUrl = "/responses";
		    }
		    HttpSession session = request.getSession(false);
            if(session != null) {
            	session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
		    redirectStrategy.sendRedirect(request, response, redirectUrl);
	}
    
}
