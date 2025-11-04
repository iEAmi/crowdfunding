package ir.guru.user.user;

import static ir.guru.user.user.UserRegistrationException.invalidUsernameException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class UserRegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;

    void register(String username, String password) throws UserRegistrationException {
        if (userDetailsManager.userExists(username)) throw invalidUsernameException(username);

        final var encodedPassword = passwordEncoder.encode(password);
        final var userDetails = User.withUsername(username)
                .password(encodedPassword)
                .roles("USER")
                .build();

        userDetailsManager.createUser(userDetails);
    }
}
