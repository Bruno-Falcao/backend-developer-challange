package com.cayena.backenddeveloper.controller;

import com.cayena.backenddeveloper.exceptions.NotFoundException;
import com.cayena.backenddeveloper.model.Product;
import com.cayena.backenddeveloper.service.ProductService;
import com.cayena.backenddeveloper.utils.ResponseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Return a paginated list all products.
     *
     * @param page The page number, starts from 0.
     * @param pageSize Represents the max number of products in each page.
     * @return A ResponseEntity containing the paginated list of products as the response body
     */
    @GetMapping("/find_all")
    public ResponseEntity<Object> findAllProducts(@RequestParam int page, @RequestParam("page_size") int pageSize) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.findAllProducts(page, pageSize))));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format(e.getMessage()),
                            Arrays.stream(e.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }

    /**
     * Returns a product by its ID.
     *
     * @param id of the product to return.
     * @return A ResponseEntity with the appropriate HTTP status code and response body containing the product.
     * @throws NotFoundException when the product is not found.
     */
    @GetMapping("/find_by_id")
    public ResponseEntity<Object> findProductsById(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.findProductById(id))));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format(e.getMessage()),
                            Arrays.stream(e.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }

    /**
     * Saves a new product.
     *
     * @param product The product to be saved.
     * @return A ResponseEntity with the HTTP code of 200 and a response body with the Success String.
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.saveProduct(product))));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format(e.getMessage()),
                            Arrays.stream(e.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }

    /**
     * Updates a specified product with the new data and updates the dateOfLastUpdate field using the current date.
     *
     * @param product The updated product data.
     * @return A ResponseEntity with HTTP code of 200 and response body containing a result String.
     */

    @PutMapping("/update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.updateProduct(product))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format(e.getMessage()),
                            Arrays.stream(e.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }

    /**
     * Deletes a product based on ID.
     *
     * @param id of the product to be deleted.
     * @return ResponseEntity with 200 HTTP status code and a response body with Success String.
     * @throws NotFoundException if the product is not found.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProduct(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.deleteProduct(id))));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format(e.getMessage()),
                            Arrays.stream(e.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }

    /**
     * Updates the stock quantity of a product.
     *
     * @param productId The ID of the product.
     * @param stockNumber The new stock quantity for the product.
     * @return ResponseEntity with the success String and status code of 200.
     * @throws NotFoundException if the product with the given ID is not found.
     */
    @PatchMapping("/update_stock")
    public ResponseEntity<Object> updateStock(@RequestParam("product_id") Integer productId,
                                              @RequestParam("stock_number") Integer stockNumber) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.updateQuantity(productId, stockNumber))));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format(e.getMessage()),
                            Arrays.stream(e.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }
}
