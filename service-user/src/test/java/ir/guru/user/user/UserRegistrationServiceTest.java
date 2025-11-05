package ir.guru.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsManager userDetailsManager;

    private UserRegistrationService service;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<UserRegistrationService> constructor =
                UserRegistrationService.class.getDeclaredConstructor(PasswordEncoder.class, UserDetailsManager.class);
        constructor.setAccessible(true);
        service = constructor.newInstance(passwordEncoder, userDetailsManager);
    }

    @Test
    void registerThrowsWhenUsernameAlreadyExists() {
        String username = "taken-user";
        when(userDetailsManager.userExists(username)).thenReturn(true);

        assertThatThrownBy(() -> service.register(username, "password"))
                .isInstanceOf(UserRegistrationException.InvalidUsernameException.class)
                .hasMessageContaining(username);

        verifyNoInteractions(passwordEncoder);
        verify(userDetailsManager, never()).createUser(any());
    }

    @Test
    void registerCreatesNewUserWithEncodedPassword() throws Exception {
        String username = "new-user";
        String password = "plain-secret";
        String encoded = "encoded-secret";

        when(userDetailsManager.userExists(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encoded);

        service.register(username, password);

        ArgumentCaptor<UserDetails> userCaptor = ArgumentCaptor.forClass(UserDetails.class);
        verify(userDetailsManager).createUser(userCaptor.capture());

        UserDetails created = userCaptor.getValue();
        verify(passwordEncoder).encode(password);
        assertThat(created.getUsername()).isEqualTo(username);
        assertThat(created.getPassword()).isEqualTo(encoded);
        assertThat(created.getAuthorities())
                .anySatisfy(authority -> assertThat(authority.getAuthority()).isEqualTo("ROLE_USER"));
    }
}
