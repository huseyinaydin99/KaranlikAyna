package tr.com.huseyinaydin.user;

import java.util.List;
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
import tr.com.huseyinaydin.configuration.CurrentUser;
import tr.com.huseyinaydin.email.EmailService;
import tr.com.huseyinaydin.file.FileService;
import tr.com.huseyinaydin.user.dto.UserUpdate;
import tr.com.huseyinaydin.user.exception.ActivationNotificationException;
import tr.com.huseyinaydin.user.exception.InvalidTokenException;
import tr.com.huseyinaydin.user.exception.NotFoundException;
import tr.com.huseyinaydin.user.exception.NotUniqueEmailException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileService fileService;

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

    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);
        if(inDB == null) {
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }

    public Page<User> getUsers(Pageable page, CurrentUser currentUser) {
        if(currentUser == null) {
            return userRepository.findAll(page);
        }
        return userRepository.findByIdNot(currentUser.getId(), page);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(long id, UserUpdate userUpdate) {
        User inDB = getUser(id);
        inDB.setUsername(userUpdate.username());
        if(userUpdate.image() != null) {
            String fileName = fileService.saveBase64StringAsFile(userUpdate.image());
            fileService.deleteProfileImage(inDB.getImage());
            inDB.setImage(fileName);
        }
        return userRepository.save(inDB);
    }
}