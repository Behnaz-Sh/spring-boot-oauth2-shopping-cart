package com.github.behnazsh.junit;

import com.github.behnazsh.dao.entity.CartItem;
import com.github.behnazsh.dao.entity.Product;
import com.github.behnazsh.dao.repository.CartItemRepository;
import com.github.behnazsh.service.CartItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Behnaz Sh
 */
public class CartItemTests {
    private CartItemService cartItemService;
    private CartItemRepository cartItemRepositoryMock;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Before
    public void setUp() {
        cartItemRepositoryMock = Mockito.mock(CartItemRepository.class);
        cartItemService = new CartItemService(cartItemRepositoryMock);
    }

    @Test
    public void crudCartItemSuccessfully() {
        when(cartItemRepositoryMock.findById(eq(Long.parseLong("1")))).thenReturn(Optional.empty());
        doAnswer(returnsFirstArg()).when(cartItemRepositoryMock).save(any(CartItem.class));

        CartItem cartItem= cartItemService.addOrUpdateCartItem(mockCartItem());
        assertEquals(Long.valueOf(1), cartItem.getId());
        cartItemService.deleteCartItem(cartItem.getId());
        assertNull(cartItemRepositoryMock.getOne(cartItem.getId()));
    }

    private CartItem mockCartItem() {
        CartItem cartItem= new CartItem();

        Product product = new Product();
        product.setId(Long.parseLong("1"));
        product.setVersion(Long.parseLong("0"));
        product.setCode("P2000000002");
        product.setName("Bag");
        product.setDesc("crossbody");
        product.setPrice(Double.parseDouble("10"));

        cartItem.setId(Long.parseLong("1"));
        cartItem.setCartId(1L);
        cartItem.setQuantity(20);
        cartItem.setProduct(product);
        cartItem.setAmount(10.00);

        return cartItem;
    }
}
