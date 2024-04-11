package com.in28minutes.rest.webservices.restfulwebservices.users.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.users.model.User;
import com.in28minutes.rest.webservices.restfulwebservices.users.srv.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserProvisioningController
{

    
    private final UserDetailsServiceImpl service;

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User userInfo)
    {
        return service.addUser(userInfo);
    }

}
