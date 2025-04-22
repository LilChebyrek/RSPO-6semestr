package com.example.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Lab3Application {
	public static void main(String[] args) {
		SpringApplication.run(Lab3Application.class, args);
	}
	@GetMapping("/author")
	public String author(@RequestParam(value = "name", defaultValue = "Logashov Danila IP-217") String author) {
		return String.format("%s", author);
	}
}