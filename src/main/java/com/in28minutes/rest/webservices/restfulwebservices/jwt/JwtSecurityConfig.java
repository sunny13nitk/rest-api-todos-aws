package com.in28minutes.rest.webservices.restfulwebservices.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.in28minutes.rest.webservices.restfulwebservices.config.OptionsRequestMatcher;
import com.in28minutes.rest.webservices.restfulwebservices.users.srv.UserDetailsServiceImpl;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfig
{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {

        // @formatter:off
        // h2-console is a servlet
        // https://github.com/spring-projects/spring-security/issues/12310
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                                               .requestMatchers(new AntPathRequestMatcher("/authenticate")).permitAll()
                                               .requestMatchers(new AntPathRequestMatcher("/auth/addNewUser")).permitAll()
                                               //h2-console is a servlet and NOT recommended for a production
                                               //.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() 
                                                 // Response to preflight request doesn't pass access control check
                                               .requestMatchers(new OptionsRequestMatcher("/**")).permitAll()
                                               .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .headers(header ->
                {
                    header.frameOptions((frameOptions) -> frameOptions.sameOrigin());
                }).build();

                // @formatter:on
    }

    // @Bean
    // public AuthenticationManager authenticationManager(UserDetailsService
    // userDetailsService)
    // {
    // var authenticationProvider = new DaoAuthenticationProvider();
    // authenticationProvider.setUserDetailsService(userDetailsService);
    // return new ProviderManager(authenticationProvider);
    // }

    // @Bean
    // public UserDetailsService userDetailsService()
    // {
    // UserDetails user =
    // User.withUsername("sunny").password("{noop}dummy").authorities("read").roles("USER").build();

    // return new InMemoryUserDetailsManager(user);
    // }

    // User Creation
    @Bean
    @Primary
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsServiceImpl();
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()
    {
        JWKSet jwkSet = new JWKSet(rsaKey());
        return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource)
    {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException
    {
        return NimbusJwtDecoder.withPublicKey(rsaKey().toRSAPublicKey()).build();
    }

    @Bean
    public RSAKey rsaKey()
    {

        KeyPair keyPair = keyPair();

        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString()).build();
    }

    @Bean
    public KeyPair keyPair()
    {
        try
        {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to generate an RSA Key Pair", e);
        }
    }

}
