package com.category.api.controller;

import com.category.api.facade.CategoryFacade;
import com.category.api.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryApiController {

    private final CategoryFacade categoryFacade;

    @Autowired
    public CategoryApiController(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    @RequestMapping("/**/categories/{categoryId}/discounted-products")
    public @ResponseBody
    Products getDiscountedProducts(@PathVariable String categoryId,
                                  @RequestParam(name = "labelType", required = false, defaultValue = "showWasNow") String labelType) {

        return categoryFacade.getDiscountedProductForCategory(categoryId, labelType);

    }
}
