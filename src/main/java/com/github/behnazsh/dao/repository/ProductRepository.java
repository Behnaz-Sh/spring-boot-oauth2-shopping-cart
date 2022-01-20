package com.github.behnazsh.dao.repository;

import com.github.behnazsh.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Behnaz Sh
 */

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {

    @Query(value = "SELECT * FROM products p WHERE p.product_code= :code LIMIT 1", nativeQuery = true)
    Optional<Product> findByCode(String code);

    @Query(value = "SELECT * FROM products p WHERE p.product_name= :name AND p.ID!= :id LIMIT 1", nativeQuery = true)
    Optional<Product> productNameExist(String name, Long id);

    @Query(value = "SELECT 1 FROM cart_items ci WHERE ci.product_id = :id", nativeQuery = true)
    Integer cartHasItem(Long id);
}
