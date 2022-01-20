package com.github.behnazsh.conroller;

import com.github.behnazsh.common.RestUtil;
import com.github.behnazsh.conroller.model.CartItemDto;
import com.github.behnazsh.dao.entity.*;
import com.github.behnazsh.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Behnaz Sh
 */

@RestController
@RequestMapping(value = "/behnazsh/v1/cart")
@Api(tags = {"cartItem"})
public class CartItemController {

	private static final Logger LOG = getLogger(CartItemController.class);
	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MessageSource messageSource;

	@PostMapping(value="/addCartItem/{productId}",  consumes = {"application/json"} , produces = {"application/json"})
	@ApiOperation(value = "Add/update a cart Item", notes = "Returns added/updated cart item")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity addCartItem(@PathVariable("productId") Long productId) {
		Optional<Product> product = RestUtil.checkResourceAvailable(productService.findProductById(productId),
				messageSource.getMessage("product.notFound", null, Locale.ENGLISH));
		User user = RestUtil.checkResourceAvailable(userDetailService.getUser(),
				messageSource.getMessage("user.notFound", null, Locale.ENGLISH));
		Optional<Cart> cartOpt = cartService.getCartByUserId(user.getId());
		Cart cart = cartOpt.orElse(new Cart());
		CartItem crEdCartItem;

		if (cartOpt.isPresent()){
			Set<CartItem> cartItems = cart.getCartItems();
			for (CartItem cartItem: cartItems){
				if (product.get().getId().equals(cartItem.getProduct().getId())) {
					cartItem.setQuantity(cartItem.getQuantity() + 1);
					cartItem.setAmount(cartItem.getQuantity() * cartItem.getProduct().getPrice());
					crEdCartItem = cartItemService.addOrUpdateCartItem(cartItem);
					LOG.info("Updated cart with id: {} and for the cart item with id: {}" , cart.getId() , crEdCartItem.getId());
					return ResponseEntity.ok(modelMapper.map(crEdCartItem , CartItemDto.class));
				}
			}
		} else {
			cart.setUser(user);
			cartService.saveCart(cart);
		}
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(1);
		cartItem.setProduct(product.orElse(null));
		cartItem.setAmount(product.get().getPrice());
		cartItem.setCartId(cart.getId());
		crEdCartItem = cartItemService.addOrUpdateCartItem(cartItem);
		LOG.info("Added to the cart with id: {} and for the item with id: {}" , cart.getId(), crEdCartItem.getId());
		return ResponseEntity.ok(modelMapper.map(crEdCartItem , CartItemDto.class));
	}

    @DeleteMapping(value = "/deleteCartItem/{id}")
	@ApiOperation(value = "Delete a cart item", notes = "You should provide a valid cart item Id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCartItem(@PathVariable(value = "id") Long id) {
		RestUtil.checkResourceAvailable(cartItemService.getCartItemById(id),
				messageSource.getMessage("cart.item.notFound", null, Locale.ENGLISH));
		cartItemService.deleteCartItem(id);
	}
}