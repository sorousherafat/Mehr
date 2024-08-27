package org.mehr.desktop.model.api.responses;

public class StockPatchResponse {
    private boolean success;
    private String description;

    public String getDescription() {
        return description;
    }

    public boolean isSuccess() {
        return success;
    }
}
