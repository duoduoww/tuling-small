package com.tuling.tulingparentson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tuling.*")
public class TulingParentSonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TulingParentSonApplication.class, args);
	}

}
