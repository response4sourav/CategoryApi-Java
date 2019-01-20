package com.category.api.service;

import com.category.api.model.Price;

public interface ProductService {

    String getHexColor(String basicColor);

    boolean hasPriceReduction(Price price);

    String formattedPrice(Object price, String currency);

    String formatPriceLabel(Price price, String labelType);

    double getPriceReduction(Price price);
}
