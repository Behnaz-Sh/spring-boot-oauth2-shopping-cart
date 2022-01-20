package com.github.behnazsh.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Behnaz Sh
 */

@Entity
@Table(name = "cart_items")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {
    private static final long serialVersionUID = 7567326115328760412L;

    @Column(name = "cart_id")
    private Long cartId;

    private Integer quantity;
    private Double amount;

    @OneToOne
    @JoinColumn(name = "product_id",
            nullable = false,
            referencedColumnName="id",
            foreignKey = @ForeignKey(name = "cart_product_fk"))
    private Product product;

}
