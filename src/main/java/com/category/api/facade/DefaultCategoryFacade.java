package com.category.api.facade;

import com.category.api.model.Products;
import com.category.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DefaultCategoryFacade implements CategoryFacade {

    private final CategoryService categoryService;
    private final ProductFacade productFacade;

    @Autowired
    public DefaultCategoryFacade(CategoryService categoryService, ProductFacade productFacade) {
        this.categoryService = categoryService;
        this.productFacade = productFacade;
    }

    @Override
    public Products getDiscountedProductForCategory(String categoryId, String labelType) {

        Products products = categoryService.getProductsForCategory(categoryId);
        if(!StringUtils.isEmpty(products.getErrorMessage())) return products;

        if(CollectionUtils.isEmpty(products.getProducts())) {
            products.setErrorMessage("Didn't found any product under category " + categoryId);
            return products;
        }

        return productFacade.populateDiscountedProductsData(products.getProducts(), labelType);
    }



}
