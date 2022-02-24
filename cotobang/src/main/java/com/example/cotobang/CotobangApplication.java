package com.example.cotobang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CotobangApplication {

    public static void main(String[] args) {
        SpringApplication.run(CotobangApplication.class, args);
    }

}
