package com.example.interview_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class InterviewHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewHubApplication.class, args);
        System.out.println("Hello World");
    }

}
