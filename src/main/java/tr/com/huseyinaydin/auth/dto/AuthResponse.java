package tr.com.huseyinaydin.auth.dto;

import tr.com.huseyinaydin.auth.token.Token;
import tr.com.huseyinaydin.user.dto.UserDTO;

public class AuthResponse {
    UserDTO user;
    Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}