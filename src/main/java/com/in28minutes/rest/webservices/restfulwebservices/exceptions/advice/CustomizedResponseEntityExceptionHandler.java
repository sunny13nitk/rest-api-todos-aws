package com.in28minutes.rest.webservices.restfulwebservices.exceptions.advice;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.in28minutes.rest.webservices.restfulwebservices.exceptions.ToDoNotFoundException4User;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ToDoNotFoundException4User.class)
    public final ResponseEntity<ProblemDetail> handlePostNotFound4UserException(Exception ex, WebRequest request)
            throws Exception
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://todoApp/errors/not-found"));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        return new ResponseEntity<ProblemDetail>(problemDetail, HttpStatus.NOT_FOUND);
    }
}
