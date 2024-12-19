package tr.com.huseyinaydin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tr.com.huseyinaydin.auth.dto.AuthResponse;
import tr.com.huseyinaydin.auth.dto.Credentials;
import tr.com.huseyinaydin.auth.exception.AuthenticationException;
import tr.com.huseyinaydin.error.ApiError;
import tr.com.huseyinaydin.shared.GenericMessage;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/v1/auth")
    public ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
        var authResponse = authService.authenticate(creds);
        var cookie = ResponseCookie.from("karanlikayna-token", authResponse.getToken().getToken()).path("/")
                .httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/api/v1/logout")
    public ResponseEntity<GenericMessage> handleLogout(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
            @CookieValue(name = "karanlikayna-token", required = false) String cookieValue) {
        var tokenWithPrefix = authorizationHeader;
        if (cookieValue != null) {
            tokenWithPrefix = "AnyPrefix " + cookieValue;
        }
        authService.logout(tokenWithPrefix);
        var cookie = ResponseCookie.from("karanlikayna-token", "").path("/").maxAge(0).httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new GenericMessage("Çıkış başarılı çerezler geçersiz hale getirildi."));
    }
}