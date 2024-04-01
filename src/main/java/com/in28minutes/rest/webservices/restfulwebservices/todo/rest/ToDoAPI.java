package com.in28minutes.rest.webservices.restfulwebservices.todo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.exceptions.ToDoNotFoundException4User;
import com.in28minutes.rest.webservices.restfulwebservices.todo.Todo;
import com.in28minutes.rest.webservices.restfulwebservices.todo.TodoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class ToDoAPI
{
    private final TodoService todoSrv;

    private static final String relUserTodos = "allToDos for User";

    @GetMapping("/{userName}/todos")
    public CollectionModel<EntityModel<Todo>> retrieveTodos4User(@PathVariable String userName)
    {
        CollectionModel<EntityModel<Todo>> cM = null;
        List<EntityModel<Todo>> todos = null;
        List<Todo> todosList = null;

        if (StringUtils.hasText(userName))
        {
            todosList = todoSrv.findByUsername(userName);
            if (!CollectionUtils.isEmpty(todosList))
            {
                todos = new ArrayList<EntityModel<Todo>>();
                for (Todo todo : todosList)
                {
                    if (todo != null)
                    {
                        EntityModel<Todo> eM = EntityModel.of(todo);
                        // Add link in future for each to do
                        // WebMvcLinkBuilder link4eachPost =
                        // linkTo(methodOn(this.getClass()).getPosts4UserById(userId));
                        // eM.add(link2Posts.withRel(relUserPosts));
                        todos.add(eM);
                    }

                }

                cM = CollectionModel.of(todos);
            }
        }

        return cM;

    }

    @GetMapping("/{userName}/simpletodos")
    public List<Todo> retrieveTodos4UserDirect(@PathVariable String userName)
    {

        List<Todo> todosList = null;

        if (StringUtils.hasText(userName))
        {
            todosList = todoSrv.findByUsername(userName);
        }
        return todosList;

    }

    @GetMapping("/{userName}/todos/{id}")
    public ResponseEntity<EntityModel<Todo>> getToDos4UserbyToDoId(@PathVariable String userName, @PathVariable int id)
    {
        EntityModel<Todo> eM = null;
        if (StringUtils.hasText(userName))
        {

            // Get Posts for User
            if (todoSrv != null)
            {
                Optional<Todo> currToDoO = todoSrv.findById(id);
                if (currToDoO.isPresent())
                {
                    eM = EntityModel.of(currToDoO.get());
                    WebMvcLinkBuilder link2UserToDos = linkTo(methodOn(this.getClass()).retrieveTodos4User(userName));
                    eM.add(link2UserToDos.withRel(relUserTodos));

                }
                else
                {
                    throw new ToDoNotFoundException4User("No ToDo with Id : " + id + " found for User : " + userName);
                }

            }

        }
        return new ResponseEntity<>(eM, HttpStatus.OK);

    }

}
