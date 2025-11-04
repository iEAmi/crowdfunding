package ir.guru.user.user;

public record AccessToken(String accessToken, String tokenType, long expiresIn) {

    static AccessToken bearerToken(String accessToken, long expiresIn) {
        return new AccessToken(accessToken, "Bearer", expiresIn);
    }
}
