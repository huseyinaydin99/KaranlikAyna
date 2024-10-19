package tr.com.huseyinaydin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import tr.com.huseyinaydin.user.User;
import tr.com.huseyinaydin.user.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User inDB = userService.findByEmail(email);
        if (inDB == null) {
            throw new UsernameNotFoundException(email + " ile kayıtlı bir kullanıcı bulunmamakta.");
        }

        return new CurrentUser(inDB);
    }
}