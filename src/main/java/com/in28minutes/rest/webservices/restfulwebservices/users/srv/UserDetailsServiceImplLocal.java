package com.in28minutes.rest.webservices.restfulwebservices.users.srv;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.in28minutes.rest.webservices.restfulwebservices.users.model.User;

@Service
public class UserDetailsServiceImplLocal implements UserDetailsService
{

    @Autowired
    private PasswordEncoder encoder;

    //@formatter:off
    //Creating Local Im Meory Users to TEst Multiple User(s) validation in addition to DBUser(s)
    private  List<User> usersList ;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        User userInMemory = null;
        addUsers();
        Optional<User> user =
                // SCan local Memory Users too
                usersList.stream().filter(u -> u.getUsername().equalsIgnoreCase(userName)).findFirst();
        if (user.isPresent())
        {
            userInMemory = user.get();
            userInMemory.setPassword(getEncodedPassword(userInMemory.getPassword()));

        }

        user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));

        return new UserDetailsImpl(userInMemory);
    }

    public void addUsers()
    {
        //@formatter:off
       this.usersList =  Arrays.asList(

                new User(null, "testuser", encoder.encode("dummy"), true, "USER"),
                new User(null, "testadmin", encoder.encode("dummy"), true, "ADMIN"));
        //@formatter:on
    }

    public String getEncodedPassword(String pwd)
    {
        String encPwd = null;
        if (encoder != null)
        {
            encPwd = encoder.encode(pwd);
        }

        return encPwd;
    }

}