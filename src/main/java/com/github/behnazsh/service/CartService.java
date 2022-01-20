package com.github.behnazsh.service;

import com.github.behnazsh.dao.entity.Cart;
import com.github.behnazsh.dao.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Behnaz Sh
 */

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;

	public Optional<Cart> getCartById (Long id) {
		return cartRepository.findById(id);
	}

	public Optional<Cart> getCartByUserId(Long id) {
		return cartRepository.getCartByUserId(id);
	}

	public void saveCart(Cart cart) {
		cartRepository.save(cart);
	}


}
