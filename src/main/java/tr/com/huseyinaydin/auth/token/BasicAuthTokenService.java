package tr.com.huseyinaydin.auth.token;

import java.util.Base64;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.auth.dto.Credentials;
import tr.com.huseyinaydin.user.User;

@Service
public class BasicAuthTokenService implements TokenService {

    @Override
    public Token createToken(User user, Credentials creds) {
        String emailColonPassword = creds.email() + ":" + creds.password();
        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        throw new UnsupportedOperationException("Unimplemented method 'verifyToken'");
    }
}