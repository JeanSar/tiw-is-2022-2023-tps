package fr.univlyon1.m2tiw.is.chainmanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ChainManagerApplication {

	public static void main(String[] args) {
		log.info("STARTING THE APPLICATION");
		SpringApplication.run(ChainManagerApplication.class, args);
		log.info("APPLICATION FINISHED");
	}
}
