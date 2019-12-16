package com.savannah.util.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

/**
 * 可以为null
 * @author stalern
 * @date 2019/12/16~17:28
 * @deprecated 因为使用了RestAPI，直接把json转为实体，所以不存在null
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({PARAMETER})
public @interface Nullable {
}
