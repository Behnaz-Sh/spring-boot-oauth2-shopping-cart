package com.github.behnazsh.conroller;

import com.github.behnazsh.common.RestUtil;
import com.github.behnazsh.conroller.model.CartDto;
import com.github.behnazsh.dao.entity.Cart;
import com.github.behnazsh.dao.entity.User;
import com.github.behnazsh.service.CartService;
import com.github.behnazsh.service.UserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Behnaz Sh
 */

@RestController
@RequestMapping(value = "/behnazsh/v1/cart")
@Api(tags = {"cart"})
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Get user's cart", notes = "You should provide a valid cart id")
    public ResponseEntity getCart(){
        User user = RestUtil.checkResourceAvailable(userDetailService.getUser(),
                messageSource.getMessage("user.notFound", null, Locale.ENGLISH));
        Optional<Cart> cart= RestUtil.checkResourceAvailable(cartService.getCartByUserId(user.getId()),
                messageSource.getMessage("cart.noCartExists", null, Locale.ENGLISH));
        BeanUtils.copyProperties(CartDto.class,cart.orElse(new Cart()));
        return ResponseEntity.ok(cart.orElse(new Cart()));
    }
}