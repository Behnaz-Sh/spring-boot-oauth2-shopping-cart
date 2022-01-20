package com.github.behnazsh.conroller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Behnaz Sh
 */

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends BaseDto {
    @NotBlank(message = "{product.name.notNull}")
    private String code;

    @NotBlank(message = "{product.name.notNull}")
    private String name;

    @NotNull(message = "{product.amount.notNull}")
    @DecimalMin(message = "{product.amount.invalidRange}" ,value="0",inclusive=false)
    @Digits(message ="{product.amount.invalidRange}", integer=4,fraction=2)
    private Double price;

    private String desc;

    public ProductDto(@NotNull Long id,
                      @NotBlank(message = "{product.name.notNull}") String name,
                      @NotNull(message = "{product.amount.notNull}")
                      @DecimalMin(message = "{product.amount.invalidRange}", value = "0", inclusive = false)
                      @Digits(message = "{product.amount.invalidRange}", integer = 4, fraction = 2) Double price) {
        super.setId(id);
        this.name = name;
        this.price = price;
    }
}
