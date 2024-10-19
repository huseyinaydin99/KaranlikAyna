package tr.com.huseyinaydin.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import jakarta.validation.Valid;
import tr.com.huseyinaydin.auth.token.TokenService;
import tr.com.huseyinaydin.configuration.CurrentUser;
import tr.com.huseyinaydin.error.ApiError;
import tr.com.huseyinaydin.shared.GenericMessage;
import tr.com.huseyinaydin.shared.Messages;
import tr.com.huseyinaydin.user.dto.UserCreate;
import tr.com.huseyinaydin.user.dto.UserDTO;
import tr.com.huseyinaydin.user.dto.UserUpdate;
import tr.com.huseyinaydin.user.exception.ActivationNotificationException;
import tr.com.huseyinaydin.user.exception.AuthorizationException;
import tr.com.huseyinaydin.user.exception.InvalidTokenException;
import tr.com.huseyinaydin.user.exception.NotFoundException;
import tr.com.huseyinaydin.user.exception.NotUniqueEmailException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @CrossOrigin
    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        // System.err.println("Uygulama dili - tarayıcı dili: " +
        // LocaleContextHolder.getLocale().getLanguage());
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("KaranlikAyna.create.user.success.message",
                LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @PatchMapping("/api/v1/users/{token}/active")
    public GenericMessage activateUser(@PathVariable String token) {
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("KaranlikAyna.activate.user.success.message",
                LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @GetMapping("/api/v1/users")
    // public Page<UserDTO> getUsers(Pageable page,
    // @RequestHeader(name="Authorization", required = false) String
    // authorizationHeader){
    public Page<UserDTO> getUsers(Pageable page, @AuthenticationPrincipal CurrentUser currentUser) {
        // var loggedInUser = tokenService.verifyToken(authorizationHeader);
        // return userService.getUsers(page, loggedInUser).map(UserDTO::new);
        return userService.getUsers(page, currentUser).map(UserDTO::new);
    }

    @GetMapping("/api/v1/users/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return new UserDTO(userService.getUser(id));
    }

    @PutMapping("/api/v1/users/{id}")
    // public UserDTO updateUser(@PathVariable long id, @Valid @RequestBody
    // UserUpdate userUpdate, @RequestHeader(name="Authorization", required = false)
    // String authorizationHeader){
    public UserDTO updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate,
            @AuthenticationPrincipal CurrentUser currentUser) {

        // if(loggedInUser == null || loggedInUser.getId() != id) { //farkı bir
        // kullanıcıyı güncellemeyi önlemek.
        if (currentUser.getId() != id) { // farkı bir kullanıcıyı güncellemeyi önlemek.
            throw new AuthorizationException();
        }
        return new UserDTO(userService.updateUser(id, userUpdate));
    }
}