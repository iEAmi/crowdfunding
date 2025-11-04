package ir.guru.user.user;

import java.time.Clock;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class UserAuthenticationService {
    private final Clock clock;
    private final JwtEncoder jwtEncoder;
    private final JwtTokenProperties jwtTokenProperties;
    private final AuthenticationManager authenticationManager;

    AccessToken login(final String username, final String password) {
        final var authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        final var authentication = authenticationManager.authenticate(authenticationToken);

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

        return AccessToken.bearerToken(
                jwt.getTokenValue(), jwtTokenProperties.accessTokenTtl().toSeconds());
    }
}
