package tr.com.huseyinaydin.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import tr.com.huseyinaydin.shared.Messages;

import java.util.Collections;
import java.util.Map;

public class NotUniqueEmailException extends RuntimeException{
    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("KaranlikAyna.error.validation", LocaleContextHolder.getLocale()));
    }
    
    public Map<String, String> getValidationErrors(){
        return Collections.singletonMap("email", Messages.getMessageForLocale("KaranlikAyna.constraint.email.notunique", LocaleContextHolder.getLocale()));
    }
}