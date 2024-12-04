package tr.com.huseyinaydin.auth.token;

import javax.crypto.SecretKey;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.huseyinaydin.auth.dto.Credentials;
import tr.com.huseyinaydin.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
@Primary
public class JwtTokenService implements TokenService{

    SecretKey key = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Token createToken(User user, Credentials creds) {
        /*String token = Jwts.builder().setSubject(Long.toString(user.getId())).signWith(key).compact();
        return new Token("Bearer", token);*/

        TokenSubject tokenSubject = new TokenSubject(user.getId(), user.isActive());
        try {
            String subject = mapper.writeValueAsString(tokenSubject);
            String token = Jwts.builder().setSubject(subject).signWith(key).compact();
            return new Token("Bearer", token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        if(authorizationHeader == null) return null;
        //var token = authorizationHeader.split("Bearer ")[1];
        var token = authorizationHeader.split(" ")[1];
        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            Jws<Claims> claims = parser.parseClaimsJws(token);
            //long userId = Long.valueOf(claims.getBody().getSubject());
            var subject = claims.getBody().getSubject();
            var tokenSubject = mapper.readValue(subject, TokenSubject.class);
            User user = new User();
            //user.setId(userId);
            user.setId(tokenSubject.id());
            user.setActive(tokenSubject.active());
            return user;
        } catch (JwtException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static record TokenSubject(long id, boolean active) {}
}