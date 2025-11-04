package ir.guru.user.user;

import lombok.Getter;

public abstract sealed class UserRegistrationException extends Exception {
    private UserRegistrationException(String message) {
        super(message);
    }

    static InvalidUsernameException invalidUsernameException(String username) {
        return new InvalidUsernameException(username);
    }

    @Getter
    public static final class InvalidUsernameException extends UserRegistrationException {
        private final String username;

        private InvalidUsernameException(String username) {
            super("Invalid username: " + username);
            this.username = username;
        }
    }
}
