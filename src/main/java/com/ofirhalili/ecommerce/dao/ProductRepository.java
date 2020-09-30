package com.ofirhalili.ecommerce.dao;

import com.ofirhalili.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
