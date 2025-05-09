package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfig {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Active Profile: " + activeProfile);
        };
    }
} 