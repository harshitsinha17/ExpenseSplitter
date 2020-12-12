package com.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.controller")
public class MoneySplitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneySplitterApplication.class, args);
	}

}
