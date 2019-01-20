package com.category.api.facade;

import com.category.api.model.Product;
import com.category.api.model.Products;

import java.util.List;

public interface ProductFacade {

    Products populateDiscountedProductsData(List<Product> products, String labelType);
}
