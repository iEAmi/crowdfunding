package ir.guru.user.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFacade {
    private final UserRegistrationService userRegistrationService;
    private final UserAuthenticationService userAuthenticationService;

    // TODO: write test, test that this method delegate to userAuthenticationService
    public AccessToken login(String username, String password) {
        return userAuthenticationService.login(username, password);
    }

    // TODO: write test, test that this method delegate to userRegistrationService
    public void register(String username, String password) throws UserRegistrationException {
        userRegistrationService.register(username, password);
    }
}
