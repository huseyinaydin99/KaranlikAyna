package tr.com.huseyinaydin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import tr.com.huseyinaydin.user.User;
import tr.com.huseyinaydin.user.UserRepository;

@SpringBootApplication
/*(exclude = SecurityAutoConfiguration.class)*/ //spring security artık devrede. devre dışı olayını kaldırdım.
public class KaranlikAynaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaranlikAynaApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public CommandLineRunner userCreator(UserRepository userRepository, PasswordEncoder passwordEncoder){
		//PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //artık new'lemeye gerek yok IoC'dan DI parametreden alıyorum. Aslında bu yönteme metot enjeksiyonu deniyor.
		return (args) -> {
			for (var i = 1; i <= 25; i++) {
				User user = new User();
				user.setUsername("user" + i);
				user.setEmail("user" + i + "@mail.com");
				user.setPassword(passwordEncoder.encode("P4ssword"));
				user.setActive(true);
				/*user.setFirstName("first" + i);
				user.setLastName("last" + i);
				if (i == 1) {
					user.setImage("my-profile-image.png");
				}*/
				userRepository.save(user);
			}
		};
	}
}