package tr.com.huseyinaydin.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import tr.com.huseyinaydin.shared.Messages;

public class ActivationNotificationException extends RuntimeException{

    public ActivationNotificationException(){
        super(Messages.getMessageForLocale("KaranlikAyna.create.user.email.failure", LocaleContextHolder.getLocale()));
    }   
}