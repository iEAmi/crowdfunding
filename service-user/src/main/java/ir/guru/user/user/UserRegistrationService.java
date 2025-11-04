package ir.guru.user.user;

import ir.guru.user.user.web.AuthController;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class UserRegistrationService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(
            final UserDetailsManager userDetailsManager,
            final PasswordEncoder passwordEncoder
    ) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthController.RegisterResponse register(final String username, final String password) {
        if (userDetailsManager.userExists(username)) {
            throw new ResponseStatusException(CONFLICT, "username-already-exists");
        }

        final var encodedPassword = passwordEncoder.encode(password);
        final var userDetails = User.withUsername(username)
                .password(encodedPassword)
                .roles("USER")
                .build();

        userDetailsManager.createUser(userDetails);

        return new AuthController.RegisterResponse(username);
    }
}
