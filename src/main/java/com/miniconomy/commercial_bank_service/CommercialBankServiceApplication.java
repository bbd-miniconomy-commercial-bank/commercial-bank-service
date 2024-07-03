package com.miniconomy.commercial_bank_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommercialBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommercialBankServiceApplication.class, args);
	}

}
