package tr.com.huseyinaydin.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import tr.com.huseyinaydin.configuration.KaranlikAynaProperties;

@Service
public class EmailService {

    private JavaMailSenderImpl mailSender;

    @Autowired
    private KaranlikAynaProperties karanlikAynaProperties;

    @Autowired
    private MessageSource messageSource;

    /*
     * public EmailService(){
     * this.initialize();
     * }
     */

    @PostConstruct // hazırlayıcı metot çalıştıktan hemen sonra.
    public void initialize() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(karanlikAynaProperties.getEmail().host());
        mailSender.setPort(karanlikAynaProperties.getEmail().port());
        mailSender.setUsername(karanlikAynaProperties.getEmail().username());
        mailSender.setPassword(karanlikAynaProperties.getEmail().password());
        // mailSender.setPassword("FPC8Rgpy5A9n7Wk1jf-"); //hataya neden olması için
        // koydum. MailException oluşsunda Transactional dipnotu çalışsın ve rollback
        // etsin diye.
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}">${clickHere}</a>
                </body>
            </html>
            """;

    public void sendActivationEmail(String email, String activationToken) {
        var activationUrl = karanlikAynaProperties.getClient().host() + "/activation/" + activationToken;
        /*
         * SimpleMailMessage message = new SimpleMailMessage();
         * message.setFrom(karanlikAynaProperties.getEmail().from());
         * message.setTo(email);
         * message.setSubject("Account Activation");
         * message.setText(activationUrl);
         * this.mailSender.send(message);
         */
        var title = messageSource.getMessage("KaranlikAyna.mail.user.created.title", null,
                LocaleContextHolder.getLocale());
        var clickHere = messageSource.getMessage("KaranlikAyna.mail.click.here", null, LocaleContextHolder.getLocale());
        var mailBody = activationEmail
                .replace("${url}", activationUrl)
                .replace("${title}", title)
                .replace("${clickHere}", clickHere);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom(karanlikAynaProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }

    public void sendPasswordResetEmail(String email, String passwordResetToken) {
        String passwordResetUrl = karanlikAynaProperties.getClient().host() + "/password-reset/set?tk="
                + passwordResetToken;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        var title = "Şifre Sıfırlama";
        var clickHere = messageSource.getMessage("KaranlikAyna.mail.click.here", null, LocaleContextHolder.getLocale());
        var mailBody = activationEmail.replace("${url}", passwordResetUrl).replace("${title}", title)
                .replace("${clickHere}", clickHere);
        try {
            message.setFrom(karanlikAynaProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.mailSender.send(mimeMessage);
    }
}