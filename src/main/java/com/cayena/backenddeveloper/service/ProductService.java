package com.cayena.backenddeveloper.service;

import com.cayena.backenddeveloper.exceptions.NotFoundException;
import com.cayena.backenddeveloper.model.Product;
import com.cayena.backenddeveloper.repository.ProductRepository;
import com.cayena.backenddeveloper.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cayena.backenddeveloper.utils.Utils.currentDate;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository repository;

    public ProductService(ProductRepository productRepository, SupplierRepository repository) {
        this.productRepository = productRepository;
        this.repository = repository;
    }

    /**
     * Retrieves a list of all products.
     *
     * @return A list of Product objects representing all the products in the system.
     */
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        isEmptyList(products);
        return products;
    }

    /**
     * Find a product using the id as parameter
     *
     * @param id
     * @return
     */
    public Optional<Product> findProductById(Integer id) {
        return Optional.of(productRepository.findById(id))
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

    private boolean saveProductValidation(Product product) {
        return product.getId() == null && product.getName() != null && product.getQuantity() >= 0
                && product.getUnitPrice() != null && product.getUnitPrice().compareTo(BigDecimal.ZERO) > 0
                && product.getDateOfCreation() == null
                && product.getDateOfCreation().isBefore(product.getDateOfLastUpdate());
    }

    private void isEmptyList(List<Product> products) {
        if (products.isEmpty()) throw new NotFoundException("No products found");
    }
}
