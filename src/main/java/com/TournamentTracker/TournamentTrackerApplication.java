package com.TournamentTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.TournamentTracker"})
public class TournamentTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentTrackerApplication.class, args);
	}

}
