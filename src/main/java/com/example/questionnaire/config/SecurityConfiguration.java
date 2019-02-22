package com.example.questionnaire.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.questionnaire.service.UserDetailsServiceImpl;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean 
    public AccessDeniedHandler accessDeniedHandler(){
		return new CustomAccessDeniedHandler();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
		.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/login/**").permitAll()
		.antMatchers("/access-denied/**").permitAll()
		.antMatchers("/css/**", "/js/**", "/images/**").permitAll()
		.antMatchers("/login-error/**").permitAll()
		.antMatchers("/forgot-password/**").permitAll()
		.antMatchers("/validate-token/**").permitAll()
		.antMatchers("/signup/**").permitAll()
		.antMatchers("/signup-success/**").permitAll()
		.antMatchers("/").hasRole("USER")
		.antMatchers("/success/**").hasRole("USER")
		.antMatchers("/my-responses/**").hasRole("USER")
		.antMatchers("/load-user-data/**").hasRole("USER")
		.antMatchers("/get-personal-responses-page").hasRole("USER")
		.antMatchers("/check-changes/**").hasRole("USER")
		.antMatchers("/edit-profile/**").hasRole("USER")
		.antMatchers("/change-password/**").hasRole("USER")
		.antMatchers("/fields").hasRole("ADMIN")
		.antMatchers("/get-fields-page").hasRole("ADMIN")
		.antMatchers("/get-responses-page").hasRole("ADMIN")
		.antMatchers("/responses").hasRole("ADMIN")
		.antMatchers("/reset-password/**").hasRole("CHANGE_PASSWORD")
		.anyRequest()
		.authenticated().and().csrf().disable().formLogin()
		.loginPage("/login").successHandler(new CustomAuthenticationSuccessHandler()).failureUrl("/login-error")
		.usernameParameter("email")
		.passwordParameter("password")
		.and().logout()
		.logoutSuccessUrl("/login").and()
		.rememberMe().key("uniqueAndSecret").rememberMeParameter("remember_me")
		.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**", "/static/**");
	}
}
