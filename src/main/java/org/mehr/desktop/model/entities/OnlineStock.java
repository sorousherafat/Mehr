package org.mehr.desktop.model.entities;

import java.util.Objects;

public final class OnlineStock {
    private final int id;
    private final String title;
    private final int stock;

    public OnlineStock(int id, String title, int stock) {
        this.id = id;
        this.title = title;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        OnlineStock that = (OnlineStock) obj;
        return id == that.id && stock == that.stock && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, stock);
    }

    @Override
    public String toString() {
        return "OnlineStock[" + "id=" + id + ", title=" + title + ", stock=" + stock + ']';
    }
}
