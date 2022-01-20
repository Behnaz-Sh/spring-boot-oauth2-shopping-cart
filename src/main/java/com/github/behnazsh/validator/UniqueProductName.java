package com.github.behnazsh.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author Behnaz Sh
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, METHOD, TYPE, ANNOTATION_TYPE})
@Constraint(validatedBy = UniqueProductNameValidator.class)
public @interface UniqueProductName {
    String message() default "{UniqueProductName}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
