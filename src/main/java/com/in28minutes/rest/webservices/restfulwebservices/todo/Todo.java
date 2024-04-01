package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Todo
{

	private int id;

	private String username;

	private String description;
	private LocalDate targetDate;
	private boolean done;

}