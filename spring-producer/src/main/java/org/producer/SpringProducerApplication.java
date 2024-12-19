package org.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringProducerApplication.class, args);
    }
}