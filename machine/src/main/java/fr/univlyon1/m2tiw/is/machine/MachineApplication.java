package fr.univlyon1.m2tiw.is.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachineApplication.class, args);
	}

}
