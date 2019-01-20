package com.category.api.model;

public enum CurrencySymbol {

    GBP ("£"),
    EUR ("€"),
    USD ("$");

    private final String symbol;

    private CurrencySymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getCurrencySymbol() {
        return symbol;
    }
}
