package com.cayena.backenddeveloper;

import com.cayena.backenddeveloper.exceptions.NotFoundException;
import com.cayena.backenddeveloper.model.Product;
import com.cayena.backenddeveloper.model.Supplier;
import com.cayena.backenddeveloper.repository.ProductRepository;
import com.cayena.backenddeveloper.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.cayena.backenddeveloper.utils.Utils.currentDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void findAllProductsSuccess() {
        List<Product> expectedProducts = Collections.singletonList(new Product());
        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> products = productService.findAllProducts();

        assertNotNull(products);
        assertEquals(expectedProducts, products);
    }

    @Test
    void findAllProductsThrowsExceptionWhenNoProductsFound() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> productService.findAllProducts());
    }

    @Test
    void findProductByIdShouldSuccessWhenSpecifiedProductFound() {
        Integer productId = 1;
        Product specifiedProduct = new Product();
        specifiedProduct.setId(productId);
        specifiedProduct.setName("Lettuce");

        when(productRepository.findById(productId)).thenReturn(Optional.of(specifiedProduct));

        Product result = productService.findProductById(productId);

        assertEquals(specifiedProduct, result);
    }

    @Test
    void findProductByIdShouldFailWhenProductNotFound() {
        Integer productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findProductById(productId));
    }

    @Test
    void saveProductShouldSuccessWhenPassValidation() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setQuantity(10);
        product.setUnitPrice(BigDecimal.valueOf(20.0));

        when(productRepository.save(product)).thenReturn(product);

        String result = productService.saveProduct(product);

        assertEquals("Product saved successfully", result);
        assertEquals(currentDate(), product.getDateOfCreation());
        assertNull(product.getDateOfLastUpdate());
    }

    @Test
    void saveProductShouldFailWhenValidationFail() {
        Product product = new Product();
        product.setId(1);
        product.setName(null);
        product.setQuantity(-5);
        product.setUnitPrice(BigDecimal.valueOf(-10.0));

        assertThrows(Exception.class, () -> productService.saveProduct(product));
    }

    @Test
    void updateProductShouldSuccessWhenPassValidations() throws Exception {
        Integer productId = 1;
        Supplier supplier = new Supplier();
        Product existingProduct = new Product();

        supplier.setId(1);
        supplier.setDateOfCreation("15/05/2023");
        supplier.setName("Pepper Store");

        existingProduct.setId(productId);
        existingProduct.setName("Onion");
        existingProduct.setQuantity(10);
        existingProduct.setUnitPrice(BigDecimal.valueOf(20.0));
        existingProduct.setSupplierId(supplier);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Pepper");
        updatedProduct.setQuantity(5);
        updatedProduct.setUnitPrice(BigDecimal.valueOf(15.0));
        updatedProduct.setSupplierId(supplier);
        updatedProduct.setDateOfCreation("15/05/2023");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        String result = productService.updateProduct(updatedProduct);

        assertEquals("Product updated successfully", result);
        assertEquals(updatedProduct.getName(), existingProduct.getName());
        assertEquals(updatedProduct.getQuantity(), existingProduct.getQuantity());
        assertEquals(updatedProduct.getUnitPrice(), existingProduct.getUnitPrice());
        assertEquals(updatedProduct.getSupplierId(), existingProduct.getSupplierId());
        assertNotNull(existingProduct.getDateOfLastUpdate());
    }

    @Test
    void updateProductShouldFailWhenProductNotFound() {
        Integer productId = 1;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.updateProduct(updatedProduct));
    }

    @Test
    void updateProductShouldFailWhenValidationIsNotSatisfied() {
        Integer productId = 1;
        Product existingProduct = new Product();

        existingProduct.setId(productId);
        existingProduct.setName(null);
        existingProduct.setQuantity(10);
        existingProduct.setUnitPrice(BigDecimal.valueOf(20.0));

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        assertThrows(Exception.class, () -> productService.updateProduct(updatedProduct));
    }

    @Test
    void deleteExistingProduct() {
        Integer productId = 1;
        Product dbProduct = new Product();
        dbProduct.setId(productId);
        Optional<Product> product = Optional.of(dbProduct);
        when(productRepository.findById(productId)).thenReturn(product);

        String result = productService.deleteProduct(productId);

        assertEquals("Product deleted", result);
    }

    @Test
    void deleteProductShouldThrowExceptionWhenProductNotFound() {
        Integer productId = 1;
        Optional<Product> optionalProduct = Optional.empty();
        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        assertThrows(NotFoundException.class, () -> productService.deleteProduct(productId));
    }

    @Test
    public void testUpdateQuantity() {
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setQuantity(10);

        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));

        String result = productService.updateQuantity(1, 20);

        assertEquals("Stock updated successfully", result);
        assertEquals(20, existingProduct.getQuantity());
    }

    @Test
    public void testUpdateQuantityWithNegativeStockNumber() {
        assertThrows(NotFoundException.class, () -> {
            productService.updateQuantity(1, -10);
        });
    }

    @Test
    public void testUpdateQuantityWithNonExistingProduct() {
        Mockito.when(productRepository.findById(1)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            productService.updateQuantity(1, 20);
        });
    }
}
