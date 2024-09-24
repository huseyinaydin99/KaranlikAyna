package tr.com.huseyinaydin.user;

import java.util.UUID;
//import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
/*import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;*/
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tr.com.huseyinaydin.email.EmailService;
import tr.com.huseyinaydin.user.exception.ActivationNotificationException;
import tr.com.huseyinaydin.user.exception.NotUniqueEmailException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackOn = MailException.class)
    public void save(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user); //kendi catch'imize düşmesi için kullandık. Transactional dipnotu kullanılınca Spring bize Proxy nesne yaratıyor ve orada da try catch yapısı mevcut.
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
        }
        catch(DataIntegrityViolationException ex){
            throw new NotUniqueEmailException();
        }
        catch (MailException ex) {
            throw new ActivationNotificationException();
        }
    }
}