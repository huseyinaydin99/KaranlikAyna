package tr.com.huseyinaydin.error;

import java.util.stream.Collectors;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import tr.com.huseyinaydin.auth.exception.AuthenticationException;
import tr.com.huseyinaydin.shared.Messages;
import tr.com.huseyinaydin.user.exception.ActivationNotificationException;
import tr.com.huseyinaydin.user.exception.InvalidTokenException;
import tr.com.huseyinaydin.user.exception.NotFoundException;
import tr.com.huseyinaydin.user.exception.NotUniqueEmailException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            NotUniqueEmailException.class,
            ActivationNotificationException.class,
            InvalidTokenException.class,
            NotFoundException.class,
            AuthenticationException.class
    })
    public ResponseEntity<?> handleException(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        if (exception instanceof MethodArgumentNotValidException) {
            String message = Messages.getMessageForLocale("KaranlikAyna.error.validation",
                    LocaleContextHolder.getLocale());
            apiError.setMessage(message);
            apiError.setStatus(400);
            var validationErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()
                    .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                            (existing, replacing) -> existing));
            apiError.setValidationErrors(validationErrors);
        } else if (exception instanceof NotUniqueEmailException) {
            apiError.setStatus(400);
            apiError.setValidationErrors(((NotUniqueEmailException) exception).getValidationErrors());
        } else if (exception instanceof ActivationNotificationException) {
            apiError.setStatus(502);
        } else if (exception instanceof InvalidTokenException) {
            apiError.setStatus(400);
        } else if (exception instanceof NotFoundException) {
            apiError.setStatus(404);
        } else if (exception instanceof AuthenticationException) {
            apiError.setStatus(401);
        }
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}