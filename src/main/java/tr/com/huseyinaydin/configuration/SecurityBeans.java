package tr.com.huseyinaydin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityBeans {
    
    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}