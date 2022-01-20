package com.github.behnazsh.dao.repository;

import com.github.behnazsh.dao.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Behnaz Sh
 */

@Repository
public interface CartRepository extends JpaRepository<Cart, Long > {

    @Query(value = "SELECT * FROM carts c WHERE c.user_id= :id LIMIT 1", nativeQuery = true)
    Optional<Cart> getCartByUserId(Long id);
}
