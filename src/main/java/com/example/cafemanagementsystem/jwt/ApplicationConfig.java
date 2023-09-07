package com.example.cafemanagementsystem.jwt;

import com.example.cafemanagementsystem.model.Users;
import com.example.cafemanagementsystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements UserDetailsService {
    private final UserRepo userRepo;
    private Users users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        users = userRepo.findByEmail(username);
        if (!Objects.isNull(users))
            return new User(users.getEmail(), users.getPassword(), new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not found.");
    }

    public Users getUserDetail() {
        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(/*userDetailsService()*/ this);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
