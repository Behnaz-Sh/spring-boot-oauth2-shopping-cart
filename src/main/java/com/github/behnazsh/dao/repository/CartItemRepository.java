package com.github.behnazsh.dao.repository;

import com.github.behnazsh.dao.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Behnaz Sh
 */

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long > {
}
