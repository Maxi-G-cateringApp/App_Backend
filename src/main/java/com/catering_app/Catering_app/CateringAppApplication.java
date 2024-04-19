package com.catering_app.Catering_app;

import com.catering_app.Catering_app.model.Role;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CateringAppApplication  implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CateringAppApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		User admin = userRepository.findByRole(Role.ADMIN);
		if (admin == null){
			User user = new User();
			user.setName("admin1995");
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin1234"));
			user.setPhoneNumber("2212345574");
			user.setRole(Role.ADMIN);
			user.setActive(true);
			user.setOtp("1234");
			userRepository.save(user);
		}
	}
}
