package org.mehr.desktop.model.api.states;

import org.mehr.desktop.model.entities.OnlineStock;

import java.util.Map;

public class StockFirstPageState {
    private final int total;
    private final int count;
    private final Map<String, OnlineStock> stocks;

    public StockFirstPageState(int total, int count, Map<String, OnlineStock> stocks) {
        this.total = total;
        this.count = count;
        this.stocks = stocks;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public int getRemainingPages(int pageSize) {
        return (int) Math.ceil((float) (total - count) / pageSize);
    }

    public Map<String, OnlineStock> getStocks() {
        return stocks;
    }
}
