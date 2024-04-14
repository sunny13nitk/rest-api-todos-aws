package com.in28minutes.rest.webservices.restfulwebservices.customAuth;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException
    {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        if ("memuser".equals(username) && "dummy".equals(password))
        {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }
        else
        {
            throw new UsernameNotFoundException(username + " not found.");
        }
    }

    @Override
    public boolean supports(Class<?> auth)
    {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}