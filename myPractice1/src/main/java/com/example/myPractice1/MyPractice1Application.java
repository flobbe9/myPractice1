package com.example.myPractice1;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class MyPractice1Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MyPractice1Application.class, args);
	}

}
