package com.api.calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan
@EntityScan("com.api.calendar")
@EnableJpaRepositories("com.api.calendar.repository")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CalendarApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CalendarApplication.class, args);
	}

}
