package org.mehr.desktop.model.id;

import java.util.Objects;

public final class IDRecord {
    private final String topThreeCode;
    private final String portalCode;

    public IDRecord(String topThreeCode, String portalCode) {
        this.topThreeCode = topThreeCode;
        this.portalCode = portalCode;
    }

    public String getTopThreeCode() {
        return topThreeCode;
    }

    public String getPortalCode() {
        return portalCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        IDRecord that = (IDRecord) obj;
        return Objects.equals(this.topThreeCode, that.topThreeCode) &&
                Objects.equals(this.portalCode, that.portalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topThreeCode, portalCode);
    }

    @Override
    public String toString() {
        return "IDRecord[" +
                "topThreeCode=" + topThreeCode + ", " +
                "portalCode=" + portalCode + ']';
    }


}
