package com.savannah.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author stalern
 * @date 2019/12/28~21:44
 */
@Target( {TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckTimeValidator.class)
@Documented
@NotBlank
public @interface TimeReasonable {

    String message() default "{com.savannah.util.validator.TimeReasonable.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

