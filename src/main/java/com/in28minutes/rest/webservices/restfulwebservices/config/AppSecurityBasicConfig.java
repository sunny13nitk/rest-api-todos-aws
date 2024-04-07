package com.in28minutes.rest.webservices.restfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class AppSecurityBasicConfig
{

    /*
     * Configuring User Details with customization rather than in properties file
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder)
    {
        UserDetails user = User.withUsername("sunny").password(passwordEncoder.encode("dummy")).roles("USER").build();

        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }

    // Filter Chain Config
    // Authenticate all requests using basic authentication
    // disabling csrf
    // stateless api
    @Bean
    public SecurityFilterChain filterChainBasic(HttpSecurity http) throws Exception
    {

        // @formatter:off
        return
        http.authorizeHttpRequests(auth -> 
                                        auth
                                        // Response to preflight request doesn't pass access control check
                                        .requestMatchers(new OptionsRequestMatcher("/**")).permitAll()
                                        .anyRequest().authenticated()) // Authenticate all requests
             .httpBasic(Customizer.withDefaults())// with basic Authentication
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// stateless
                                                                                                          // session
             .csrf(csrf -> csrf.disable())// disable csrf
             .build();

        // @formatter:on
    }

}
