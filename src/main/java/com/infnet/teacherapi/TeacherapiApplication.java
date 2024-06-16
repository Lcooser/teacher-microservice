package com.infnet.teacherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeacherapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherapiApplication.class, args);
		System.out.println("Microservice is running");
	}

}
