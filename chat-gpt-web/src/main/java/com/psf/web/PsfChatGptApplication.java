package com.psf.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.psf")
@EntityScan("com.psf.core.persistence.entity")
@EnableJpaRepositories("com.psf.core.persistence.dao")
public class PsfChatGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsfChatGptApplication.class, args);
    }

}
