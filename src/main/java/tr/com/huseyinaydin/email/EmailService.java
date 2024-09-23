package tr.com.huseyinaydin.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tr.com.huseyinaydin.configuration.KaranlikAynaProperties;

@Service
public class EmailService {

    JavaMailSenderImpl mailSender;

    @Autowired
    private KaranlikAynaProperties karanlikAynaProperties;

    /*public EmailService(){
        this.initialize();
    }*/

    @PostConstruct //hazırlayıcı metot çalıştıktan hemen sonra.
    public void initialize(){
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(karanlikAynaProperties.getEmail().host());
        mailSender.setPort(karanlikAynaProperties.getEmail().port());
        mailSender.setUsername(karanlikAynaProperties.getEmail().username());
        mailSender.setPassword(karanlikAynaProperties.getEmail().password());
        //mailSender.setPassword("FPC8Rgpy5A9n7Wk1jf-"); //hataya neden olması için koydum. MailException oluşsunda Transactional dipnotu çalışsın ve rollback etsin diye.
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendActivationEmail(String email, String activationToken) {
        var activationUrl = karanlikAynaProperties.getClient().host() + "/activation/" + activationToken;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(karanlikAynaProperties.getEmail().from());
        message.setTo(email);
        message.setSubject("Account Activation");
        message.setText(activationUrl);
        this.mailSender.send(message);
    }
}