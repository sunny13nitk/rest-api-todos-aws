package com.in28minutes.rest.webservices.restfulwebservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToDoNotFoundException4User extends RuntimeException
{
    public ToDoNotFoundException4User(String msg)
    {
        super(msg);
    }
}
