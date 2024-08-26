package org.mehr.desktop.model.api.inputs;

public class AuthenticationInput {
    private final String username;
    private final String password;

    public AuthenticationInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
