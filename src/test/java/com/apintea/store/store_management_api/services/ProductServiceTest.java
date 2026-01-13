package com.apintea.store.store_management_api.services;

import com.apintea.store.store_management_api.entities.ProductEntity;
import com.apintea.store.store_management_api.exceptions.ProductNotFoundException;
import com.apintea.store.store_management_api.mappers.ProductMapper;
import com.apintea.store.store_management_api.model.Product;
import com.apintea.store.store_management_api.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private UUID productId;
    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        product = Product.builder()
                .id(productId)
                .name("Tea")
                .price(10.0)
                .quantity(5)
                .build();

        productEntity = ProductEntity.builder()
                .id(productId)
                .name("Tea")
                .price(10.0)
                .quantity(5)
                .build();
    }

    @Test
    void shouldAddProduct() {
        when(productMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(any())).thenReturn(productEntity);
        when(productMapper.toProduct(productEntity)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertNotNull(result);
        assertEquals("Tea", result.getName());
        verify(productRepository).save(productEntity);
    }

    @Test
    void addProduct_shouldThrowException_whenProductIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct(null)
        );

        assertEquals("Product cannot be null!", ex.getMessage());
        verifyNoInteractions(productRepository);
    }

    @Test
    void getProduct_shouldReturnProduct_whenExists() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.toProduct(productEntity)).thenReturn(product);

        Product result = productService.getProduct(productId);

        assertEquals(productId, result.getId());
        assertEquals("Tea", result.getName());
        assertEquals(10,  result.getPrice());
        assertEquals(5,  result.getQuantity());
    }

    @Test
    void getProduct_shouldThrowException_whenNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProduct(productId));
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        when(productMapper.toProduct(productEntity)).thenReturn(product);

        List<Product> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Tea", result.get(0).getName());
        assertEquals(10.0,  result.get(0).getPrice());
        assertEquals(5,  result.get(0).getQuantity());
    }

    @Test
    void deleteProduct_shouldDelete_whenExists() {
        when(productRepository.existsById(productId)).thenReturn(true);

        UUID result = productService.deleteProduct(productId);

        assertEquals(productId, result);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_shouldThrowException_whenNotFound() {
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(productId));
    }

    @Test
    void changePrice_shouldUpdatePrice() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.toProduct(productEntity)).thenReturn(product);
        when(productMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        Product result = productService.changePrice(productId, 12.0);

        assertEquals(12.0, result.getPrice());
    }

    @Test
    void changePrice_shouldThrowException_whenPriceInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> productService.changePrice(productId, -7.0));
    }
}
