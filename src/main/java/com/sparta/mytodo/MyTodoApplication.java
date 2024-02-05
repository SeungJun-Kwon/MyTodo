package com.sparta.mytodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTodoApplication.class, args);
	}

}
