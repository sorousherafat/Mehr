package org.mehr.desktop.model.entities;

import java.util.Objects;

public final class ID {
    private final String code;
    private final String portal;

    public ID(String code, String portal) {
        this.code = code;
        this.portal = portal;
    }

    public String getCode() {
        return code;
    }

    public String getPortal() {
        return portal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ID that = (ID) obj;
        return Objects.equals(this.code, that.code) && Objects.equals(this.portal, that.portal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, portal);
    }

    @Override
    public String toString() {
        return "IDRecord[" + "code=" + code + ", portal=" + portal + ']';
    }


}
