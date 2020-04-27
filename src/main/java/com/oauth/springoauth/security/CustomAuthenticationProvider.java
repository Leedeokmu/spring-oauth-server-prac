package com.oauth.springoauth.security;

import com.oauth.springoauth.user.User;
import com.oauth.springoauth.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepo userJpaRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User foundUser = userJpaRepo.findByUid(name).orElseThrow(() -> {
            throw new UsernameNotFoundException("username is not valid");
        });
        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            throw new BadCredentialsException("password is not valid");
        }
        return new UsernamePasswordAuthenticationToken(name, password, foundUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication
                .equals(UsernamePasswordAuthenticationToken.class);
    }
}
