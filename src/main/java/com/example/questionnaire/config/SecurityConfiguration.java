package com.example.questionnaire.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.questionnaire.service.UserDetailsServiceImpl;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
    /*@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;
    @Override
    protected void configure(AuthenticationManagerBuilder amb)throws Exception{
    	amb.authenticationProvider(authenticationProvider());
    	//amb.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery).dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }
    */
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
                .antMatchers("/authenticate/**").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/signup/create-user/**").permitAll()
                .antMatchers("/success/**").permitAll()
                .antMatchers("/fields/**").permitAll()
                .antMatchers("/j_spring_security_check/**").permitAll()
                .antMatchers("/login-error/**").permitAll()
                .antMatchers("/responses/**").hasRole("USER")
                .antMatchers("/edit-profiles/**").hasRole("USER")
                .antMatchers("/change-password/**").hasRole("USER")
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").permitAll().defaultSuccessUrl("/responses").failureUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/"))
                .logoutSuccessUrl("/login").and()
                .rememberMe().key("uniqueAndSecret");
    }
   
    @Override
    public void configure(WebSecurity web) {
    	web.ignoring().antMatchers("/resources/**", "/static/**");
    }
    
   /* @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
          = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
     
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
*/
}
