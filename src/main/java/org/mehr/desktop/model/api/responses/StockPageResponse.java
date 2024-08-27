package org.mehr.desktop.model.api.responses;

import java.util.List;

public class StockPageResponse {
    private boolean success;
    private int total;
    private int count;
    private List<StockResponse> variants;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StockResponse> getVariants() {
        return variants;
    }
}
