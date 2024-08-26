package org.mehr.desktop.model.api.responses;

import java.util.List;

public class StocksResponse {
    private boolean success;
    private int total;
    private int count;
    private List<StockResponse> variants;

    public boolean isSuccess() {
        return success;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public List<StockResponse> getVariants() {
        return variants;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
