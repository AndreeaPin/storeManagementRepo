package com.apintea.store.store_management_api.controllers;

import com.apintea.store.store_management_api.exceptions.ProductNotFoundException;
import com.apintea.store.store_management_api.model.Product;
import com.apintea.store.store_management_api.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addProduct(@RequestBody @Valid Product product) {
        try {
            String json = objectMapper.writeValueAsString(productService.addProduct(product));
            return ResponseEntity.status(HttpStatus.CREATED).body(json);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> getProduct(@PathVariable UUID id) {
        try {
            String json = objectMapper.writeValueAsString(productService.getProduct(id));
            return ResponseEntity.ok(json);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updatePrice(@PathVariable UUID id, @RequestParam Double price) {
        try {
            String json = objectMapper.writeValueAsString(productService.changePrice(id, price));
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(productService.deleteProduct(id).toString());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
