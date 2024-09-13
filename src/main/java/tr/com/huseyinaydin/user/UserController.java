package tr.com.huseyinaydin.user;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.huseyinaydin.shared.GenericMessage;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //@CrossOrigin
    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@RequestBody User user){
        userService.save(user);
        return new GenericMessage("Kullanıcı oluşturuldu.");
    }
}