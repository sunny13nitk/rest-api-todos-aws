package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Todo
{

	private int id;

	@Size(min = 5)
	private String username;
	@Size(min = 5)
	private String description;
	@FutureOrPresent
	private LocalDate targetDate;
	private boolean done;

}