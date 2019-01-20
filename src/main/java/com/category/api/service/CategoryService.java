package com.category.api.service;

import com.category.api.model.Products;

public interface CategoryService {

    Products getProductsForCategory(String categoryId);
}
