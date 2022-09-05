package com.example.myCinema.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Configuring password decoding behavior.
 */
@Configuration
public class PasswordConfig {
    
    @Bean
    PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder(31);
    }
}