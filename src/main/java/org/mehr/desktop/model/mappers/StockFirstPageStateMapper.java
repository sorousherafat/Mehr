package org.mehr.desktop.model.mappers;

import org.mehr.desktop.model.api.responses.StockPageResponse;
import org.mehr.desktop.model.api.states.StockFirstPageState;
import org.mehr.desktop.model.entities.OnlineStock;

import java.util.Map;

public class StockFirstPageStateMapper implements Mapper<StockPageResponse, StockFirstPageState> {
    @Override
    public StockFirstPageState map(StockPageResponse input) {
        int total = input.getTotal();
        int count = input.getCount();
        Map<String, OnlineStock> stocks = new OnlineStockMapMapper().map(input);
        return new StockFirstPageState(total, count, stocks);
    }
}
