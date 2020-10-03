package com.tikkiepayment;

import java.time.ZonedDateTime;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEncryptableProperties
@SpringBootApplication
public class TikkiePaymentApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TikkiePaymentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("TIMEZONE: {}", TimeZone.getDefault());
		log.info("java TIME now is: {}", new java.util.Date());
		log.info("zone is: {}", ZonedDateTime.now());

	}
}
