package com.savannah.util.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 邮箱验证器
 * @author stalern
 * @date 2019/12/15~19:46
 */
@Component
public class CheckEmailValidator implements ConstraintValidator<IsEmail, String> {

   @Override
   public void initialize(IsEmail constraint) {

   }

   @Override
   public boolean isValid(String obj, ConstraintValidatorContext context) {
      if (StringUtils.isEmpty(obj)) {
         return false;
      }
      return obj.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
   }
}
