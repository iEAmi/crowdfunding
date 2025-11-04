package ir.guru.user.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Valid
@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtTokenProperties(
        @NotNull Duration accessTokenTtl,
        @NotNull String issuer,
        @DefaultValue("user.read") List<String> defaultScopes) {}
