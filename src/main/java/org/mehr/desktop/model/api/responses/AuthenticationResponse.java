package org.mehr.desktop.model.api.responses;

public class AuthenticationResponse {
    private boolean success;
    private String description;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public String getDescription() {
        return description;
    }

    public String getToken() {
        return token;
    }
}
