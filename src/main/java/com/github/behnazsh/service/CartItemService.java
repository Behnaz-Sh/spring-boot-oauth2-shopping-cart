package com.github.behnazsh.service;

import com.github.behnazsh.dao.entity.CartItem;
import com.github.behnazsh.dao.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Behnaz Sh
 */

@Service
public class CartItemService {
	@Autowired
	private CartItemRepository cartItemRepository;

	public CartItem addOrUpdateCartItem(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	public void deleteCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}

	public Optional<CartItem> getCartItemById(Long id) {
		return cartItemRepository.findById(id);
	}
}
