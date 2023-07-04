package com.ureshii.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonConfiguration {
    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    Gson gsonConverter() {
//        return new GsonBuilder().setPrettyPrinting().create();
//    }
}
