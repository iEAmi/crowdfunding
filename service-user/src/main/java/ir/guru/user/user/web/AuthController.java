package ir.guru.user.user.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.user.user.UserFacade;
import ir.guru.user.user.UserRegistrationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/user/auth")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class AuthController {
    private final UserFacade userFacade;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        try {
            final var username = request.username();
            userFacade.register(username, request.password());
            return new RegisterResponse(username);
        } catch (UserRegistrationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest request) {
        final var accessToken = userFacade.login(request.username(), request.password());

        return new LoginResponse(accessToken.accessToken(), accessToken.tokenType(), accessToken.expiresIn());
    }

    private record LoginRequest(
            @JsonProperty("username")
            @NotBlank(message = "user.authentication.login.blank_username")
            @Size(min = 3, max = 128, message = "user.authentication.login.invalidSize_username")
            String username,

            @JsonProperty("password")
            @NotBlank(message = "user.authentication.login.blank_password")
            @Size(min = 6, max = 128, message = "user.authentication.login.invalidSize_password")
            String password) {}

    private record LoginResponse(
            @JsonProperty("accessToken") String accessToken,
            @JsonProperty("tokenType") String tokenType,
            @JsonProperty("expiresIn") long expiresIn) {}

    private record RegisterRequest(
            @JsonProperty("username")
            @NotBlank(message = "user.authentication.register.blank_username")
            @Size(min = 3, max = 128, message = "user.authentication.register.invalidSize_username")
            String username,

            @JsonProperty("password")
            @NotBlank(message = "user.authentication.register.blank_password")
            @Size(min = 6, max = 128, message = "user.authentication.register.invalidSize_password")
            String password) {}

    private record RegisterResponse(
            @JsonProperty("username") String username) {}
}
