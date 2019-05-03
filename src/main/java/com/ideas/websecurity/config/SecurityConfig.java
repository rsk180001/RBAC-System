package com.ideas.websecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(configProperties.getAdminUserName()).password(configProperties.getAdminPassword()).roles("USER", "ADMIN").and()
                .withUser(configProperties.getReadOnlyUserName()).password(configProperties.getReadOnlyPassword()).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        		http
                .authorizeRequests()
                .antMatchers("/public/**").hasAnyRole("ADMIN", "USER").and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}