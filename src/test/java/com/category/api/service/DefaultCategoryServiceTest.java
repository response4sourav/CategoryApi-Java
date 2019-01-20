package com.category.api.service;

import com.category.api.model.Products;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultCategoryServiceTest {

    @Mock
    private RestTemplateBuilder mockRestTemplateBuilder;

    private DefaultCategoryService defaultCategoryServiceUnderTest;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        defaultCategoryServiceUnderTest = new DefaultCategoryService(mockRestTemplateBuilder);
    }

    @Test
    public void testGetProductsForCategory() {
        // Setup
        final String categoryId = "categoryId";
        final Products expectedResult = null;

        // Run the test
        final Products result = defaultCategoryServiceUnderTest.getProductsForCategory(categoryId);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
