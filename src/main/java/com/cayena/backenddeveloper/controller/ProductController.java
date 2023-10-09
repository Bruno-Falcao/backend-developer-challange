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
