package org.mehr.desktop.model.api.responses;

import org.mehr.desktop.model.entities.OnlineStock;

public class StockResponse {
    private int id;
    private String title;
    private int stock;

    public OnlineStock toOnlineStock() {
        return new OnlineStock(id, title, stock);
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
}
