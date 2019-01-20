package com.category.api.service;

import com.category.api.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Enums;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements ProductService {

    @Value("${textlabel.was.now}")
    private String wasNowLabel;

    @Value("${textlabel.was.then.now}")
    private String wasThenNowLabel;

    @Value("${textlabel.percent.discount}")
    private String percDiscountLabel;

    @Override
    public String getHexColor(String basicColor) {

        if (StringUtils.isNotEmpty(basicColor) && Enums.getIfPresent(ColorRGB.class, basicColor.toUpperCase()).isPresent()) {
            return ColorRGB.valueOf(basicColor.toUpperCase()).getColorHex();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public double getPriceReduction(Price price) {
        return null != price ? getReductionAmount(getValue(price.getWas()), getValue(price.getNow())) : 0d;
    }

    @Override
    public boolean hasPriceReduction(Price price) {
        return getPriceReduction(price) > 0;
    }

    @Override
    public String formattedPrice(Object price, String currency) {
        float priceVal = getFloatValue(getValue(price));
        String currencySymbol = getCurrencySymbol(currency);
        if (priceVal >= 10) {
            return String.format("%s%.0f", currencySymbol, priceVal);
        } else {
            return String.format("%s%.2f", currencySymbol, priceVal);
        }
    }

    @Override
    public String formatPriceLabel(Price price, String labelType) {

        LabelType label = LabelType.SHOWWASNOW;
        if (Enums.getIfPresent(LabelType.class, labelType.toUpperCase()).isPresent())
            label = LabelType.valueOf(labelType.toUpperCase());

        String formattedLabel;
        switch (label) {

            case SHOWWASNOW:
                formattedLabel = getWasNowPriceLabel(price);
                break;

            case SHOWWASTHENNOW:
                formattedLabel = getWasNowThenPriceLabel(price);
                break;

            case SHOWPERCDSCOUNT:
                formattedLabel = getPercDiscountLabel(price);
                break;

            default:
                formattedLabel = getWasNowPriceLabel(price);
                break;
        }

        return formattedLabel;
    }


    private String getCurrencySymbol(String currencyCode) {
        if (StringUtils.isNotEmpty(currencyCode) && Enums.getIfPresent(CurrencySymbol.class, currencyCode.toUpperCase()).isPresent()) {
            return CurrencySymbol.valueOf(currencyCode.toUpperCase()).getCurrencySymbol();
        }
        return StringUtils.EMPTY;
    }

    private double getReductionAmount(String wasPrice, String nowPrice) {
        return getDoubleValue(wasPrice) - getDoubleValue(nowPrice);
    }

    private String getWasNowPriceLabel(Price price) {
        return String.format(wasNowLabel, formattedPrice(price.getWas(), price.getCurrency()), formattedPrice(price.getNow(), price.getCurrency()));
    }

    private String getWasNowThenPriceLabel(Price price) {
        if (StringUtils.isNotEmpty(getValue(price.getThen2()))) {
            return String.format(wasThenNowLabel, formattedPrice(price.getWas(), price.getCurrency()),
                    formattedPrice(price.getThen2(), price.getCurrency()), formattedPrice(price.getNow(), price.getCurrency()));
        } else if (StringUtils.isNotEmpty(getValue(price.getThen1()))) {
            return String.format(wasThenNowLabel, formattedPrice(price.getWas(), price.getCurrency()),
                    formattedPrice(price.getThen1(), price.getCurrency()), formattedPrice(price.getNow(), price.getCurrency()));
        }
        return getWasNowPriceLabel(price);
    }

    private String getPercDiscountLabel(Price price) {
        double wasPrice = getDoubleValue(getValue(price.getWas()));
        if (wasPrice > 0) {
            int percentDiscount = (int) Math.round((getPriceReduction(price) / wasPrice) * 100);
            return String.format(percDiscountLabel, percentDiscount, formattedPrice(price.getNow(), price.getCurrency()));
        }
        return StringUtils.EMPTY;
    }

    private String getValue(Object value) {
        if(value instanceof String) return (String) value;
        else {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(value, PriceValue.class).getFrom();
        }
    }

    private double getDoubleValue(String value) {
        return NumberUtils.isNumber(value) ? Double.parseDouble(value) : 0d;
    }

    private float getFloatValue(String value) {
        return NumberUtils.isNumber(value) ? Float.parseFloat(value) : 0f;
    }
}
