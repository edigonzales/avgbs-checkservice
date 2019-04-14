package ch.so.agi.avgbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = "ch.so.agi.avgbs")
@SpringBootApplication
public class AvgbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvgbsApplication.class, args);
	}

}
