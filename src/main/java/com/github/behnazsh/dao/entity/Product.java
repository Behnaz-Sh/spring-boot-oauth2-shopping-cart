package com.github.behnazsh.dao.entity;

import com.github.behnazsh.validator.UniqueProductName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Behnaz Sh
 */

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@UniqueProductName(message = "{product.name.duplicate}")
public class Product extends BaseEntity {
    private static final long serialVersionUID = 3940680989869679483L;

    @Column(name="product_code", unique = true, nullable = false, updatable = false)
    private String code;

    @Column(name="product_name", unique = true, nullable = false)
    @NotBlank(message = "{product.name.notNull}")
    private String name;

    @Column(name="product_price", nullable = false)
    @NotNull(message = "{product.amount.notNull}")
    @DecimalMin(message = "{product.amount.invalidRange}", value="0",inclusive=false)
    @Digits(message = "{product.amount.invalidRange}", integer=4,fraction=2)
    private Double price;

    @Column(name="product_desc")
    private String desc;

    @PrePersist
    protected void onCreate() {
        setCode("P" + ThreadLocalRandom.current().nextLong(1000000000,1999999999));
    }
}
