
package com.category.api.model;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Price {

    private Object was;
    private Object then1;
    private Object then2;
    private Object now;
    private String uom;
    private String currency;

}
