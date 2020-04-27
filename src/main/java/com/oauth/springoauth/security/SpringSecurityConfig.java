package com.oauth.springoauth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider);
    }
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                    .csrf().disable()
                    .headers().frameOptions().disable()
                .and()
                    .authorizeRequests().mvcMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*")
                    .permitAll()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();
    }
}
