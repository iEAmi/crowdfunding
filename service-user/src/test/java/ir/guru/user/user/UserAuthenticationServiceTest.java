package ir.guru.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {

    private static final Instant FIXED_INSTANT = Instant.parse("2024-01-01T10:15:30Z");

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    private UserAuthenticationService service;

    @BeforeEach
    void setUp() throws Exception {
        Clock clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
        JwtTokenProperties properties =
                new JwtTokenProperties(Duration.ofMinutes(15), "issuer.test", List.of("user.read", "user.write"));

        Constructor<UserAuthenticationService> constructor = UserAuthenticationService.class.getDeclaredConstructor(
                Clock.class, JwtEncoder.class, JwtTokenProperties.class, AuthenticationManager.class);
        constructor.setAccessible(true);
        service = constructor.newInstance(clock, jwtEncoder, properties, authenticationManager);
    }

    @Test
    void loginAuthenticatesUserAndReturnsBearerToken() {
        String username = "jane.doe";
        String password = "secret";

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(username, null, List.of());
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        Jwt jwt = new Jwt(
                "jwt-token",
                FIXED_INSTANT,
                FIXED_INSTANT.plus(Duration.ofMinutes(15)),
                Map.of("alg", "none"),
                Map.of("sub", username));
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        AccessToken token = service.login(username, password);

        ArgumentCaptor<Authentication> authenticationCaptor = ArgumentCaptor.forClass(Authentication.class);
        verify(authenticationManager).authenticate(authenticationCaptor.capture());
        Authentication usedAuthentication = authenticationCaptor.getValue();
        assertThat(usedAuthentication)
                .isInstanceOf(UsernamePasswordAuthenticationToken.class)
                .matches(auth -> username.equals(auth.getPrincipal()) && password.equals(auth.getCredentials()));

        verify(jwtEncoder).encode(any(JwtEncoderParameters.class));

        assertThat(token.accessToken()).isEqualTo(jwt.getTokenValue());
        assertThat(token.tokenType()).isEqualTo("Bearer");
        assertThat(token.expiresIn()).isEqualTo(Duration.ofMinutes(15).toSeconds());
    }
}
