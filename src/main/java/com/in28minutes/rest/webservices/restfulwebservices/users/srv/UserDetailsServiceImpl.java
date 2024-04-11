package com.in28minutes.rest.webservices.restfulwebservices.users.srv;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.in28minutes.rest.webservices.restfulwebservices.users.model.User;
import com.in28minutes.rest.webservices.restfulwebservices.users.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {

        Optional<User> user = userRepository.findByUsername(userName);

        user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));

        return user.map(UserDetailsImpl::new).get();
    }

    public String addUser(User userInfo)
    {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return "User Added Successfully";
    }

}