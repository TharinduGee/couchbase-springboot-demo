package me.tharindu.couchbase_demo_project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class, proxyBeanMethods = false)
public class CouchbaseDemoProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CouchbaseDemoProjectApplication.class, args);
	}

	public void run(String ...args) {
		log.info("Application started successfully.");
	}

}
