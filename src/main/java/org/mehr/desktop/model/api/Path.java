package org.mehr.desktop.model.api;

public class Path {
    public static final Path AUTHENTICATE = new Path("user/create-session");
    public static final Path STOCKS = new Path("manage/store/products/variants");

    private static final String prefix = "/site/api/v1/";

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public Path with(int component) {
        return with(String.valueOf(component));
    }

    public Path with(String component) {
        return new Path(this.path + "/" + component);
    }

    @Override
    public String toString() {
        return prefix + this.path;
    }
}
