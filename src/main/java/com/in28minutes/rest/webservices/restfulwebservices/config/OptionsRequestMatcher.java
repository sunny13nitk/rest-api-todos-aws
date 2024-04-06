package com.in28minutes.rest.webservices.restfulwebservices.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class OptionsRequestMatcher implements RequestMatcher
{

    private final AntPathRequestMatcher matcher;

    public OptionsRequestMatcher(String pattern)
    {
        matcher = new AntPathRequestMatcher(pattern);
    }

    @Override
    public boolean matches(HttpServletRequest request)
    {
        if (!"OPTIONS".equals(request.getMethod()))
        {
            return false;
        }

        return matcher.matches(request);
    }
}