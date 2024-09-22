package tr.com.huseyinaydin.user;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tr.com.huseyinaydin.error.ApiError;
import tr.com.huseyinaydin.shared.GenericMessage;
import tr.com.huseyinaydin.shared.Messages;
import tr.com.huseyinaydin.user.exception.NotUniqueEmailException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody User user) {
        //System.err.println("Uygulama dili - tarayıcı dili: " + LocaleContextHolder.getLocale().getLanguage());
        userService.save(user);
        String message = Messages.getMessageForLocale("KaranlikAyna.create.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("KaranlikAyna.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);
        /*Map<String, String> validationErrors = new HashMap<>();
        for(var filedError: exception.getBindingResult().getFieldErrors()){
            validationErrors.put(filedError.getField(), filedError.getDefaultMessage());
        }*/
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
            FieldError::getField,
            FieldError::getDefaultMessage, (existing, replacing) -> existing));
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleNotUniqueEmailEx(NotUniqueEmailException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400);
        apiError.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.badRequest().body(apiError);
    }
}