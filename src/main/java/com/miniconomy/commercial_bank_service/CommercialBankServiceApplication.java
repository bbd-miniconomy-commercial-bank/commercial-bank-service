package com.miniconomy.commercial_bank_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@ComponentScan("com.miniconomy.commercial_bank_service")
public class CommercialBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommercialBankServiceApplication.class, args);
	}

}
