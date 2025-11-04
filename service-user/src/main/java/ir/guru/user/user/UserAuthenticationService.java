package ir.guru.user.user;

import ir.guru.user.user.web.AuthController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
public class UserAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtTokenProperties jwtTokenProperties;
    private final Clock clock;

    public UserAuthenticationService(
            final AuthenticationManager authenticationManager,
            final JwtEncoder jwtEncoder,
            final JwtTokenProperties jwtTokenProperties,
            final Clock clock) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtTokenProperties = jwtTokenProperties;
        this.clock = clock;
    }

    public AuthController.LoginResponse login(final String username, final String password) {
        final var authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);

        final var now = Instant.now(clock);
        final var expiry = now.plus(jwtTokenProperties.accessTokenTtl());

        final var scopeValue = String.join(" ", jwtTokenProperties.defaultScopes());

        final var claims = JwtClaimsSet.builder()
                .issuer(jwtTokenProperties.issuer())
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(authentication.getName())
                .claim("scope", scopeValue)
                .build();

        final var parameters = JwtEncoderParameters.from(claims);
        final var jwt = jwtEncoder.encode(parameters);

        return new AuthController.LoginResponse(
                jwt.getTokenValue(),
                "Bearer",
                jwtTokenProperties.accessTokenTtl().toSeconds());
    }
}
