package com.category.api.facade;

import com.category.api.model.Products;

public interface CategoryFacade {

    Products getDiscountedProductForCategory(String categoryId, String labelType);

}
