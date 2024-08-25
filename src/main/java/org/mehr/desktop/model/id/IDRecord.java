package org.mehr.desktop.model.id;

import java.util.Objects;

public final class IDRecord {
    private final String code;
    private final String portal;

    public IDRecord(String code, String portal) {
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
        IDRecord that = (IDRecord) obj;
        return Objects.equals(this.code, that.code) &&
                Objects.equals(this.portal, that.portal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, portal);
    }

    @Override
    public String toString() {
        return "IDRecord[" +
                "topThreeCode=" + code + ", " +
                "portalCode=" + portal + ']';
    }


}
