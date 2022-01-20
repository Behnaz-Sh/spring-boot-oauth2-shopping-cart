package com.github.behnazsh.validator;

import com.github.behnazsh.dao.entity.Product;
import com.github.behnazsh.dao.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Behnaz Sh
 */

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, Product> {
    @Autowired
    private ProductRepository productRepository;

    public UniqueProductNameValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void initialize(UniqueProductName constraintAnnotation) {
        // initialize
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        if (product == null) {
            return true;
        }
        return !productRepository.productNameExist(product.getName(), product.getId()).isPresent();
    }
}