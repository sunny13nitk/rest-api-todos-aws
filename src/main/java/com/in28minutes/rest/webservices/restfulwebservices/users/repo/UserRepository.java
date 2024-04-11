package com.in28minutes.rest.webservices.restfulwebservices.users.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.rest.webservices.restfulwebservices.users.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

    Optional<User> findByUsername(String userName);
}