package tr.com.huseyinaydin.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/api/v1/users")
    public void createUser(){

    }
}