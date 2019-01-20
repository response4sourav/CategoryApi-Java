
package com.category.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class ColorSwatch {

    private String basicColor;
    private String color;
    private String colorSwatchUrl;
    private String imageUrl;
    private Boolean isAvailable;
    private String skuId;
    private String rgbColor;

}
