package org.mehr.desktop.model.mappers;

import org.mehr.desktop.model.api.responses.StocksResponse;
import org.mehr.desktop.model.entities.OnlineStock;

import java.util.List;
import java.util.stream.Collectors;

public class StockResponseMapper implements Mapper<StocksResponse, List<OnlineStock>> {
    @Override
    public List<OnlineStock> map(StocksResponse input) {
        return input.getVariants().stream().map(stockResponse -> {
            int id = stockResponse.getId();
            String title = stockResponse.getTitle();
            int stock = stockResponse.getStock();

            return new OnlineStock(id, title, stock);
        }).collect(Collectors.toList());
    }
}
