package tr.com.huseyinaydin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.auth.dto.AuthResponse;
import tr.com.huseyinaydin.auth.dto.Credentials;
import tr.com.huseyinaydin.auth.exception.AuthenticationException;
import tr.com.huseyinaydin.auth.token.Token;
import tr.com.huseyinaydin.auth.token.TokenService;
import tr.com.huseyinaydin.user.User;
import tr.com.huseyinaydin.user.UserService;
import tr.com.huseyinaydin.user.dto.UserDTO;

@Service
public class AuthService {
    
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public TokenService tokenService;
    public AuthResponse authenticate(Credentials creds) {
        User inDB = userService.findByEmail(creds.email());
        if(inDB == null) throw new AuthenticationException();
        if(!passwordEncoder.matches(creds.password(), inDB.getPassword())) throw new AuthenticationException();
        Token token = tokenService.createToken(inDB, creds);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(new UserDTO(inDB));
        return authResponse;
    }
}