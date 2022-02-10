package com.app.workerpool;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class WorkerpoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkerpoolApplication.class, args);
	}

	@Component
	@RequiredArgsConstructor
	class Auditor implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication == null || !authentication.isAuthenticated()) {
				return Optional.empty();
			}

			return Optional.of( authentication.getName());
		}
	}

}
