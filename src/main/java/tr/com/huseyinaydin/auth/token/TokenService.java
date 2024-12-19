package tr.com.huseyinaydin.auth.token;

import tr.com.huseyinaydin.auth.dto.Credentials;
import tr.com.huseyinaydin.user.User;

public interface TokenService {
    public Token createToken(User user, Credentials creds);
    public User verifyToken(String authorizationHeader);
    public void logout(String authorizationHeader);
}