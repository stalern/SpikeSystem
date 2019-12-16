package com.savannah.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * javax.validate的验证邮箱的添加注解
 * @author stalern
 * @date 2019/12/15~19:43
 */
@Target( {FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckEmailValidator.class)
@Documented
@NotBlank
public @interface IsEmail {

    String message() default "{com.savannah.util.validator.IsEmail.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
