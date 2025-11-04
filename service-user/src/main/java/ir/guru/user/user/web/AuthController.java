package ir.guru.user.user.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.user.user.UserAuthenticationService;
import ir.guru.user.user.UserRegistrationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRegistrationService userRegistrationService;
    private final UserAuthenticationService userAuthenticationService;

    public AuthController(
            final UserRegistrationService userRegistrationService,
            final UserAuthenticationService userAuthenticationService
    ) {
        this.userRegistrationService = userRegistrationService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody final RegisterRequest request) {
        final var userAccount = userRegistrationService.register(request.username(), request.password());
        return new RegisterResponse(userAccount.getId().toString(), userAccount.getUsername());
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody final LoginRequest request) {
        return userAuthenticationService.login(request.username(), request.password());
    }

    public static record LoginRequest(
            @JsonProperty("username")
            @NotBlank
            @Size(min = 3, max = 128)
            String username,

            @JsonProperty("password")
            @NotBlank
            @Size(min = 6, max = 128)
            String password
    ) {
    }

    public static record LoginResponse(
            @JsonProperty("access_token")
            String accessToken,

            @JsonProperty("token_type")
            String tokenType,

            @JsonProperty("expires_in")
            long expiresIn
    ) {
    }

    public static record RegisterRequest(
            @JsonProperty("username")
            @NotBlank
            @Size(min = 3, max = 128)
            String username,

            @JsonProperty("password")
            @NotBlank
            @Size(min = 6, max = 128)
            String password
    ) {
    }

    public static record RegisterResponse(
            @JsonProperty("user_id")
            String userId,

            @JsonProperty("username")
            String username
    ) {
    }
}

