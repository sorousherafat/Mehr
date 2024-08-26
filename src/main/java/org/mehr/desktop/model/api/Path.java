package org.mehr.desktop.model.api;

public enum Path {
    AUTHENTICATE("user/create-session"),
    GET_STOCKS("manage/store/products/variants");

    private static final String prefix = "/site/api/v1/";

    private final String path;

    Path(String path) {
        this.path = prefix + path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
