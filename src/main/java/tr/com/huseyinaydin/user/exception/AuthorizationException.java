package tr.com.huseyinaydin.user.exception;

public class AuthorizationException extends RuntimeException{
    
    public AuthorizationException(){
        super("Yasak kardeşim.");
    }
}