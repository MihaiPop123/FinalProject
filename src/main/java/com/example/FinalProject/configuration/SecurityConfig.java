package com.example.FinalProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeHttpRequests(auth -> {
            auth
//                    .requestMatchers("/final_project/type").permitAll() // ca sa pot adauga roluri ( in practica ar trebui protejat)
                    .requestMatchers("/final_project/register").permitAll() // pentru a inregistra un user cu un anumit rol(in practica doar un utilizator simplu ar trebui sa se poata inregistra cu rol de user)
                    .requestMatchers("/final_project/sign_in").permitAll() // pentru a loga un utilizator
                    .requestMatchers("/final_project/v1/account_type/").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/final_project/v1/auction/").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/final_project/v1/usersAccount/").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/final_project/v1/usersAccount/").hasAuthority("ADMIN")
                    .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
