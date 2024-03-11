package net.ekene.hello;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication {


    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

}