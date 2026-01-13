package com.apintea.store.store_management_api.services;

import com.apintea.store.store_management_api.entities.ProductEntity;
import com.apintea.store.store_management_api.exceptions.ProductNotFoundException;
import com.apintea.store.store_management_api.mappers.ProductMapper;
import com.apintea.store.store_management_api.model.Product;
import com.apintea.store.store_management_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null!");
        }
        log.info("Adding product: {}", product.getName());

        ProductEntity entityToSave = productMapper.toEntity(product);

        ProductEntity savedEntity = productRepository.save(entityToSave);

        return productMapper.toProduct(savedEntity);
    }


    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProduct)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + productId)
                );
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toProduct).collect(Collectors.toList());
    }

    public UUID deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(
                    "Product not found with id: " + productId);
        }

        productRepository.deleteById(productId);
        log.info("Product with {} has been deleted.", productId);
        return productId;
    }

    public Product changePrice(UUID productId, Double newPrice) {
        if (newPrice == null || newPrice < 0) {
            throw new IllegalArgumentException("The price must be greater or equal to 0");
        }
        Product product = getProduct(productId);
        product.setPrice(newPrice);
        log.info("Updated price for product {}", productId);
        productRepository.save(productMapper.toEntity(product));
        return product;
    }
}