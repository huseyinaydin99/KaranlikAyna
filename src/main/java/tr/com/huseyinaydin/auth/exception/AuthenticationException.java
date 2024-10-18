package tr.com.huseyinaydin.auth.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import tr.com.huseyinaydin.shared.Messages;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(){
        super(Messages.getMessageForLocale("KaranlikAyna.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}