package tr.com.huseyinaydin.user;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //@CrossOrigin
    @PostMapping("/api/v1/users")
    public void createUser(@RequestBody User object){
        userRepository.save(object);
    }
}