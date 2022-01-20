package com.github.behnazsh.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Behnaz Sh
 */

@Entity
@Table(name = "carts")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity{
    private static final long serialVersionUID = 2509608566996254250L;

    @OneToMany
    @JoinColumn(name = "cart_id",
            referencedColumnName="id",
            foreignKey = @ForeignKey(name = "cart_item_fk"),
            insertable = false, nullable = true, updatable = false)
    private Set<CartItem> cartItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            referencedColumnName="id",
            foreignKey = @ForeignKey(name = "cart_user_fk"),
            nullable = false, updatable = false)
    private User user;


}
