package com.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.mobile.entity"})
public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== STARTING APPLICATION ===");
        SpringApplication.run(MainApp.class, args);
        System.out.println("=== APPLICATION RUNNING ===");
    }
}
