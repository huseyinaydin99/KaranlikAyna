package tr.com.huseyinaydin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tr.com.huseyinaydin.auth.dto.AuthResponse;
import tr.com.huseyinaydin.auth.dto.Credentials;
import tr.com.huseyinaydin.auth.exception.AuthenticationException;
import tr.com.huseyinaydin.error.ApiError;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;
    
    @PostMapping("/api/v1/auth")
    public AuthResponse handleAuthentication(@Valid @RequestBody Credentials creds) {
        return authService.authenticate(creds);
    }
}