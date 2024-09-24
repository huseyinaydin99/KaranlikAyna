package tr.com.huseyinaydin.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import tr.com.huseyinaydin.shared.Messages;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super(Messages.getMessageForLocale("KaranlikAyna.activate.user.invalid.token", LocaleContextHolder.getLocale()));
    }
}