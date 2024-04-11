package com.in28minutes.rest.webservices.restfulwebservices.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest
{

    private String username;
    private String password;

}