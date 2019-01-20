package com.category.api.facade;

import com.category.api.model.ColorSwatch;
import com.category.api.model.Product;
import com.category.api.model.Products;
import com.category.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DefaultProductFacade implements ProductFacade {

    private final ProductService productService;

    @Autowired
    public DefaultProductFacade(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Products populateDiscountedProductsData(List<Product> products, String labelType) {

        log.info("Populating discounted products list... ");
        Products sortedDiscountedProducts = new Products();

        //filter products having price reduction
        List<Product> discountedProducts = products.stream()
                .filter(product -> productService.hasPriceReduction(product.getPrice()))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(discountedProducts)) {
            //sort by reduction price
            Comparator<Product> priceReductionComparator = Comparator
                    .comparing(product -> productService.getPriceReduction(product.getPrice()));
            discountedProducts.sort(priceReductionComparator.reversed());

            //format and populate discounted products data
            populateDiscountedProductsData(discountedProducts, sortedDiscountedProducts, labelType);
            log.info("Returned the list of " + discountedProducts.size() + " formatted discounted products! ");
        } else {
            sortedDiscountedProducts.setErrorMessage("No Discounted product available under this category! ");
            log.info("No Discounted product available under this category! ");
        }
        return sortedDiscountedProducts;
    }


    private void populateDiscountedProductsData(List<Product> discountedProducts, Products sortedDiscountedProducts, String labelType) {
        List<Product> discountedProductsData = new ArrayList<>();
        discountedProducts.forEach(product -> {
            Product discountedProduct = new Product();
            discountedProduct.setProductId(product.getProductId());
            discountedProduct.setTitle(product.getTitle());
            discountedProduct.setNowPrice(productService.formattedPrice(product.getPrice().getNow(), product.getPrice().getCurrency()));
            discountedProduct.setPriceLabel(productService.formatPriceLabel(product.getPrice(), labelType));
            populateColorSwatches(discountedProduct, product.getColorSwatches());
            discountedProductsData.add(discountedProduct);
        });
        sortedDiscountedProducts.setProducts(discountedProductsData);
    }

    private void populateColorSwatches(Product sortedProductData, List<ColorSwatch> colorSwatches) {
        if (!CollectionUtils.isEmpty(colorSwatches)) {
            List<ColorSwatch> fmtColorSwatches = new ArrayList<>();
            colorSwatches.forEach(colorSwatch -> {
                ColorSwatch fmtColorSwatch = new ColorSwatch();
                fmtColorSwatch.setColor(colorSwatch.getColor());
                fmtColorSwatch.setRgbColor(productService.getHexColor(colorSwatch.getBasicColor()));
                fmtColorSwatch.setSkuId(colorSwatch.getSkuId());
                fmtColorSwatches.add(fmtColorSwatch);
            });
            sortedProductData.setColorSwatches(fmtColorSwatches);
        }
    }

}
