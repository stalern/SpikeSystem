package com.savannah.util.auth;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author stalern
 * @date 2019/12/16~21:30
 */
// 运行时可以被获取
@Retention(RetentionPolicy.RUNTIME)
// 加载类上或者方法上面
@Target({METHOD,TYPE})
// 可以子类继承
@Inherited
public @interface Auth {

    /**
     * 判断权限，主要加到Controller类中
     * @return Group是一个enum类型
     */
    Group value() default Group.ANY;

}
