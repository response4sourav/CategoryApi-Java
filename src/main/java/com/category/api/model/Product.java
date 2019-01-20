
package com.category.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class Product {

    private List<Object> additionalServices;
    private String availabilityMessage;
    private String brand;
    private Boolean categoryQuickViewEnabled;
    private String code;
    private List<ColorSwatch> colorSwatches;
    private String colorWheelMessage;
    private Boolean compare;
    private String defaultSkuId;
    private String directorate;
    private String displaySpecialOffer;
    private Boolean emailMeWhenAvailable;
    private String fabric;
    private List<Object> features;
    private String image;
    private Boolean isBundle;
    private Boolean isInStoreOnly;
    private Boolean isMadeToMeasure;
    private Boolean isProductSet;
    private String nonPromoMessage;
    private Boolean outOfStock;
    private Price price;
    private String productId;
    private List<Object> promotionalFeatures;
    private Boolean swatchAvailable;
    private String swatchCategoryType;
    private String title;
    private String type;
    private String priceLabel;
    private String nowPrice;

}
