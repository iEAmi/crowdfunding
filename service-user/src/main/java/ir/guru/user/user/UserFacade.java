package ir.guru.user.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFacade {
    private final UserRegistrationService userRegistrationService;
    private final UserAuthenticationService userAuthenticationService;
}
