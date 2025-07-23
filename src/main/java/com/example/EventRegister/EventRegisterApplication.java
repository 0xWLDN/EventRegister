package com.example.EventRegister;

import com.example.EventRegister.model.User;
import com.example.EventRegister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class EventRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventRegisterApplication.class, args);
	}
}

// This component runs after application startup
@Component
class DataInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		if (userRepository.findByEmail("admin").isEmpty()) {
			User admin = new User();
			admin.setEmail("admin"); // or a proper email if needed
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole("ADMIN");
			userRepository.save(admin);
		}

		if (userRepository.findByEmail("user").isEmpty()) {
			User user = new User();
			user.setEmail("user"); // or a proper email if needed
			user.setPassword(passwordEncoder.encode("user123"));
			user.setRole("USER");
			userRepository.save(user);
		}
	}
}
