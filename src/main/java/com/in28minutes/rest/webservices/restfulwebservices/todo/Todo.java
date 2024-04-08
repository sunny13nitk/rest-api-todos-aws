package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Todo
{

	@Id
	@GeneratedValue
	private int id;

	@Size(min = 5)
	private String username;
	@Size(min = 5)
	private String description;
	@FutureOrPresent
	private LocalDate targetDate;
	private boolean done;
	
	public Todo(@Size(min = 5) String username, @Size(min = 5) String description,
			@FutureOrPresent LocalDate targetDate, boolean done)
	{
		this.username = username;
		this.description = description;
		this.targetDate = targetDate;
		this.done = done;
	}

	

}