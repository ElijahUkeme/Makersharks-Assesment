package com.makersharks.assessment;

import com.makersharks.assessment.security.entity.Role;
import com.makersharks.assessment.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class MakersharksAssesmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakersharksAssesmentApplication.class, args);


	}
		@Bean
		CommandLineRunner runner(RoleRepository repository) {
			return args -> {
				if (repository.findByName("MANAGER").isEmpty()) {
					repository.save(Role.builder()
							.name("MANAGER")
									.createdDate(LocalDateTime.now())
							.build());
				}
			};

		}

}
