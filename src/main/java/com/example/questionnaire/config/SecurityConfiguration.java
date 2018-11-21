package com.example.questionnaire.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.questionnaire.service.UserDetailsServiceImpl;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception{
    	 http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/forgot-password/**").permitAll()
                .antMatchers("/reset-password/**").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/signup/create-user/**").permitAll()
                .antMatchers("/success/**").permitAll()
                .antMatchers("/j_spring_security_check/**").permitAll()
                .antMatchers("/login-error/**").permitAll()
                .antMatchers("/fields/**").hasRole("USER")
                .antMatchers("/responses/**").hasRole("USER")
                .antMatchers("/load-user-data/**").hasRole("USER")
                .antMatchers("/check-changes/**").hasRole("USER")
                .antMatchers("/edit-profile/**").hasRole("USER")
                .antMatchers("/change-password/**").hasAnyRole("USER","CHANGE_PASSWORD")
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").permitAll().defaultSuccessUrl("/responses").failureUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutSuccessUrl("/login").and()
                .rememberMe().key("uniqueAndSecret").rememberMeParameter("remember_me");
    }
   
    @Override
    public void configure(WebSecurity web) {
    	web.ignoring().antMatchers("/resources/**", "/static/**");
    }
}
