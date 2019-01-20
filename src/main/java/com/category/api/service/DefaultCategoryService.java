package com.category.api.service;

import com.category.api.RestTemplateErrorHandler;
import com.category.api.model.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DefaultCategoryService implements CategoryService {

    private RestTemplate restTemplate;

    @Value("${category.base.url}")
    private String baseUrl;

    @Value("${category.product.endpoint}")
    private String productPath;

    @Value("${category.api.key}")
    private String apiKey;

    @Value("${category.api.connection.timeout}")
    private int connectionTimeout;

    @Value("${category.api.read.timeout}")
    private int readTimeout;

    @Autowired
    public DefaultCategoryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(connectionTimeout)
                .setReadTimeout(readTimeout)
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }

    @Override
    public Products getProductsForCategory(String categoryId) {

        StringBuilder url = new StringBuilder(baseUrl);
        url.append(categoryId).append(productPath).append(apiKey);
        log.info("Trying to fetch products from category api endpoint : " + url.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Products products = new Products();

        try {
            ResponseEntity<Products> response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, Products.class);
            if (HttpStatus.OK == response.getStatusCode()) {
                products = response.getBody();
                log.info("Successfully fetched products from category api endpoint!");
            }

        } catch (RestClientException ex) {
            log.error("Error occurred while calling category api endpoint " + ex);
            products.setErrorMessage("Error occurred while calling api endpoint " + ex.getMessage());
        }
        return products;
    }

}
