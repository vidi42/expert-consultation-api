package com.code4ro.legalconsultation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LegalConsultationApplication {

	public static void main(String[] args) {

		SpringApplication.run(LegalConsultationApplication.class, args);
	}

}
