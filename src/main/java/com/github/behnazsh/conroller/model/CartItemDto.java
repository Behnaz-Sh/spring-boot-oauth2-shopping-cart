package com.github.behnazsh.conroller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Behnaz Sh
 */

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto extends BaseDto {

    private Long cartId;
    private Integer quantity;
    private Double amount;
    private ProductDto product;

}
