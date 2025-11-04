package ir.guru.user.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import ir.guru.user.user.JwtTokenProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.UUID;

@Configuration
@EnableConfigurationProperties(JwtTokenProperties.class)
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(
            final PasswordEncoder passwordEncoder, final JwtTokenProperties jwtTokenProperties) {
        final var registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("service-user-client")
                .clientSecret(passwordEncoder.encode("service-user-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("user.read")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(jwtTokenProperties.accessTokenTtl())
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(final JwtTokenProperties jwtTokenProperties) {
        return AuthorizationServerSettings.builder()
                .issuer(jwtTokenProperties.issuer())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        final var resource = new ClassPathResource("jwks/authorization-server.jwkset");
        try (InputStream inputStream = resource.getInputStream()) {
            final var jwkSet = JWKSet.load(inputStream);
            return new ImmutableJWKSet<>(jwkSet);
        } catch (IOException | ParseException exception) {
            throw new IllegalStateException("Failed to load JWK set", exception);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder(final JWKSource<SecurityContext> jwkSource) {
        try {
            return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create JWT decoder", exception);
        }
    }
}
