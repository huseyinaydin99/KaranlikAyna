package tr.com.huseyinaydin.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import tr.com.huseyinaydin.shared.Messages;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(long id){
        super(Messages.getMessageForLocale("KaranlikAyna.user.not.found", LocaleContextHolder.getLocale(), id));
    }
}