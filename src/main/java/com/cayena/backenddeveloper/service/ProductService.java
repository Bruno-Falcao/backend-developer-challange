package com.cayena.backenddeveloper.service;

import com.cayena.backenddeveloper.exceptions.NotFoundException;
import com.cayena.backenddeveloper.model.Product;
import com.cayena.backenddeveloper.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.cayena.backenddeveloper.utils.Utils.currentDate;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<Product> findAllProducts(int page, int pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize));
        isEmptyList(products);

        return products;
    }

    
    public Product findProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public String saveProduct(Product product) throws Exception {
        if (saveProductValidation(product)) {
            product.setDateOfCreation(currentDate());
            productRepository.save(product);

            return "Product saved successfully";
        }
        throw new Exception("Error saving product");
    }

    public String updateProduct(Product product) throws Exception {
        Product existingProduct = findProductById(product.getId());

        if (updateProductValidation(product)) {
            existingProduct.setName(product.getName());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setUnitPrice(product.getUnitPrice());
            existingProduct.setSupplierId(product.getSupplierId());
            existingProduct.setDateOfLastUpdate(currentDate());
            productRepository.save(existingProduct);

            return "Product updated successfully";
        }

        throw new Exception("Product not found");
    }

    public String deleteProduct(Integer id) {
        Product existingProduct = findProductById(id);

        productRepository.delete(existingProduct);
        return "Product deleted";
    }


    public String updateQuantity(Integer productId , Integer stockNumber) {
        Product existingProduct = findProductById(productId);

        if (stockNumber >= 0 && existingProduct != null) {
            existingProduct.setQuantity(stockNumber);
            productRepository.save(existingProduct);
            return "Stock updated successfully";
        }
        throw new IllegalArgumentException("Stock number must not be negative");
    }

    private static boolean updateProductValidation(Product product) {
        return product.getName() != null && product.getQuantity() >= 0
                && product.getUnitPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean saveProductValidation(Product product) {
        return product.getId() == null && product.getName() != null && product.getQuantity() >= 0
                && product.getUnitPrice().compareTo(BigDecimal.ZERO) > 0
                && product.getDateOfCreation() == null
                && product.getDateOfLastUpdate() == null;
    }

    private void isEmptyList(Page<Product> products) {
        if (products.isEmpty()) throw new NotFoundException("No products found");
    }
}
