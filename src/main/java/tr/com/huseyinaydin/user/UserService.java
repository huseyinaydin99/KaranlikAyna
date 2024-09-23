package tr.com.huseyinaydin.user;

import java.util.UUID;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tr.com.huseyinaydin.user.exception.ActivationNotificationException;
import tr.com.huseyinaydin.user.exception.NotUniqueEmailException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackOn = MailException.class)
    public void save(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user); //kendi catch'imize düşmesi için kullandık. Transactional dipnotu kullanılınca Spring bize Proxy nesne yaratıyor ve orada da try catch yapısı mevcut.
            sendActivationEmail(user);
        }
        catch(DataIntegrityViolationException ex){
            throw new NotUniqueEmailException();
        }
        catch (MailException ex) {
            throw new ActivationNotificationException();
        }
    }

    private void sendActivationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@my-app.com");
        message.setTo(user.getEmail());
        message.setSubject("Hesap Aktivasyonu");
        message.setText("http://localhost:5173/activation/" + user.getActivationToken());
        getJavaMailSender().send(message);
    }

    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("deon.williamson23@ethereal.email");
        mailSender.setPassword("FPC8Rgpy5A9n7Wk1jf");
        //mailSender.setPassword("FPC8Rgpy5A9n7Wk1jf-"); //hataya neden olması için koydum. MailException oluşsunda Transactional dipnotu çalışsın ve rollback etsin diye.
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}