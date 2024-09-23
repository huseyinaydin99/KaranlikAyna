package tr.com.huseyinaydin.email;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    JavaMailSenderImpl mailSender;

    public EmailService(){
        this.initialize();
    }

    public void initialize(){
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("deon.williamson23@ethereal.email");
        mailSender.setPassword("FPC8Rgpy5A9n7Wk1jf");
        //mailSender.setPassword("FPC8Rgpy5A9n7Wk1jf-"); //hataya neden olması için koydum. MailException oluşsunda Transactional dipnotu çalışsın ve rollback etsin diye.
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendActivationEmail(String email, String activationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@my-app.com");
        message.setTo(email);
        message.setSubject("Account Activation");
        message.setText("http://localhost:5173/activation/"+activationToken);
        this.mailSender.send(message);
    }
}