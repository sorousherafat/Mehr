package org.mehr.desktop.model.stock;

import java.util.Objects;

public final class StockRecord {
    private final String code;
    private final String name;
    private final int stock;

    public StockRecord(String code, String name, int stock) {
        this.code = code;
        this.name = name;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        StockRecord that = (StockRecord) obj;
        return Objects.equals(this.code, that.code) &&
                Objects.equals(this.name, that.name) &&
                this.stock == that.stock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, stock);
    }

    @Override
    public String toString() {
        return "StockRecord[" +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "stock=" + stock + ']';
    }
}
