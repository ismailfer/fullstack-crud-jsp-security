package com.ismail.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    
    /**
     * Security is set up an in-memory user store with a single user. 
     * That user is given a username of "admin", a password of "admin", and a role of "ADMIN".
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
        .passwordEncoder(NoOpPasswordEncoder.getInstance())
        .withUser("admin")
        .password("admin")
        .roles("USER","ADMIN");
                
    }
    
    /**
     * The configure(HttpSecurity) method defines which URL paths should be secured and which should not. 
     * Specifically, the "/" and "/login" paths are configured to not require any authentication. 
     * All other paths must be authenticated.
     */
    protected void configure(HttpSecurity http) throws Exception
    {
     
        http.authorizeHttpRequests()
        .antMatchers("/login", "/h2-console/**")
        .permitAll()
        .antMatchers("/", "/*todo*/**")
        //.access("hasRole('USER')")
        .hasAnyRole("USER")
        .and()
        .formLogin();
        
        
    }
}
