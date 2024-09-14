package tr.com.huseyinaydin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.huseyinaydin.error.ApiError;
import tr.com.huseyinaydin.shared.GenericMessage;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/api/v1/users")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            ApiError apiError = new ApiError();
            apiError.setPath("/api/v1/users");
            apiError.setMessage("Doğrulama hatası");
            apiError.setStatus(400);
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("username", "Kullanıcı adı boş olamaz.");
            apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
        }
        userService.save(user);
        return ResponseEntity.ok(new GenericMessage("Kullanıcı oluşturuldu"));
    }
}