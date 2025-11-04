package ir.guru.user.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.Instant;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class UserRegistrationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    public UserRegistrationService(
            final UserAccountRepository userAccountRepository,
            final PasswordEncoder passwordEncoder,
            final Clock clock
    ) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.clock = clock;
    }

    @Transactional
    public UserAccount register(final String username, final String password) {
        if (userAccountRepository.existsByUsername(username)) {
            throw new ResponseStatusException(CONFLICT, "username-already-exists");
        }
        final var passwordHash = passwordEncoder.encode(password);
        final var userAccount = new UserAccount(username, passwordHash, Instant.now(clock));
        return userAccountRepository.save(userAccount);
    }
}

