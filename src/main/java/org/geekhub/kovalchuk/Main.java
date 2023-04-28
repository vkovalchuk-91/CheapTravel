package org.geekhub.kovalchuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.geekhub.kovalchuk")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}