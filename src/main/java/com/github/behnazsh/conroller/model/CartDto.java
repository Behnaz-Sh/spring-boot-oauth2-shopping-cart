package com.github.behnazsh.conroller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Behnaz Sh
 */

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class CartDto extends BaseDto {

    private Set<CartItemDto> cartItems;
    UserDto userDto;
}
