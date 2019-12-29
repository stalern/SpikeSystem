package com.savannah.util.validator;

import com.savannah.service.model.PromoDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author stalern
 * @date 2019/12/28~21:54
 */
public class CheckTimeValidator implements ConstraintValidator<TimeReasonable, PromoDTO> {
   @Override
   public void initialize(TimeReasonable constraint) {
   }

   @Override
   public boolean isValid(PromoDTO obj, ConstraintValidatorContext context) {
      if (obj == null) {
         return false;
      }
      return !obj.timeRight();
   }
}
