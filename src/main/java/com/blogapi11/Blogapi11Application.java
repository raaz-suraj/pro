package com.blogapi11;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Blogapi11Application {

	public static void main(String[] args) {
		SpringApplication.run(Blogapi11Application.class, args);
	}

	@Bean
    ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
