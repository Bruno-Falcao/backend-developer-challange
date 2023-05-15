package com.cayena.backenddeveloper.controller;

import com.cayena.backenddeveloper.model.Product;
import com.cayena.backenddeveloper.service.ProductService;
import com.cayena.backenddeveloper.utils.ResponseAPI;
import com.cayena.backenddeveloper.utils.Utils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(productService.saveProduct(product))));

        } catch (Exception e) {
            return Utils.errorResponse("Error saving the product", e);
        }
    }
}
