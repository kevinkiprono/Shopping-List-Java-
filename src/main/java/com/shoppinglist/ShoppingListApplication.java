package com.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Application for Shopping List
 * Main entry point for the REST API server
 */
@SpringBootApplication
@ComponentScan("com.shoppinglist")
public class ShoppingListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingListApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  Shopping List API Server Started");
        System.out.println("  Server running on: http://localhost:8080");
        System.out.println("  API Base URL: http://localhost:8080/api/items");
        System.out.println("========================================\n");
    }
}
