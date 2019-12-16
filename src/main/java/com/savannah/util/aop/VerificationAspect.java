package com.savannah.util.aop;

import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.util.validator.IsEmail;
import com.savannah.util.validator.ValidationResult;
import com.savannah.util.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author stalern
 * @date 2019/12/16~16:08
 */
@Aspect
@Component
public class VerificationAspect {

    private final ValidatorImpl validator;

    public VerificationAspect(ValidatorImpl validator) {
        this.validator = validator;
    }

    /**
     * 校验参数是否合法
     * 同时，如果没有Nullable注解，则不允许为null。后来发现因为我直接是Json转实体，mvc已经自动帮我校验
     * 所以，不检查Nullable，检查email
     * @param point 切面
     * @throws ReturnException 参数异常
     */
    @Before("execution(* com.savannah.controller.*.*(..))")
    public void checkParameter(JoinPoint point) throws ReturnException {
        // 获得切入方法参数
        Object[] args = point.getArgs();
        // 获得方法
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        // 获得参数，此时只是参数名和args不同
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i ++) {
            IsEmail[] type = parameters[i].getDeclaredAnnotationsByType(IsEmail.class);
            // 如果有这个注解，说明应该符合Email的格式
            if (type != null && type.length > 0) {
                String email = (String) args[i];
                if (StringUtils.isEmpty(email) || !email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
                    throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,"邮箱格式错误");
                }
            }
            // 同时对实体类进行校验
            ValidationResult result =  validator.validate(args[i]);
            if(result.isHasErrors()){
                throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
            }
        }
    }
}
