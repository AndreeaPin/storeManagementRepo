package com.apintea.store.store_management_api.repositories;

import com.apintea.store.store_management_api.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}