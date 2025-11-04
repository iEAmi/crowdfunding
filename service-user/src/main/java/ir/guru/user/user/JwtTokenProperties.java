package ir.guru.user.user;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtTokenProperties(
        Duration accessTokenTtl,
        String issuer,
        @DefaultValue("user.read") List<String> defaultScopes
) {
}

