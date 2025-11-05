package ir.guru.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private UserRegistrationService userRegistrationService;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private AccessToken accessToken;

    private UserFacade userFacade;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<UserFacade> constructor =
                UserFacade.class.getDeclaredConstructor(UserRegistrationService.class, UserAuthenticationService.class);
        constructor.setAccessible(true);
        userFacade = constructor.newInstance(userRegistrationService, userAuthenticationService);
    }

    @Test
    void loginDelegatesToAuthenticationService() {
        when(userAuthenticationService.login("john", "pass")).thenReturn(accessToken);

        AccessToken result = userFacade.login("john", "pass");

        assertThat(result).isSameAs(accessToken);
        verify(userAuthenticationService).login("john", "pass");
        verifyNoInteractions(userRegistrationService);
    }

    @Test
    void registerDelegatesToRegistrationService() throws Exception {
        userFacade.register("jane", "secret");

        verify(userRegistrationService).register("jane", "secret");
        verifyNoInteractions(userAuthenticationService);
    }
}
