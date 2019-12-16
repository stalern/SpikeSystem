package com.savannah.util.auth;

import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.service.model.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author stalern
 * @date 2019/12/16~21:42
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //获取访问页面的权限

        //获取类上的注解
        Auth authInClass = ((HandlerMethod) handler).getBean().getClass().getAnnotation(Auth.class);
        Group role = authInClass != null ? authInClass.value() : Group.ANY;
        //获取方法上的注解
        Auth authInMethod = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
        role = authInMethod != null ? authInMethod.value() : role;

        //获取Enum方法的value
        if (role == Group.ANY) {
            return true;
        }

        //获取用户的权限
        String userRole;
        boolean isLogin = request.getSession().getAttribute("IS_LOGIN") != null && (boolean) request.getSession().getAttribute("IS_LOGIN");
        if (isLogin) {
            UserDTO userDTO = (UserDTO) request.getSession().getAttribute("LOGIN_USER");
            userRole = userDTO.getRole();
            if (StringUtils.equals(String.valueOf(role), userRole)) {
                return true;
            } else {
                throw new ReturnException(EmReturnError.USER_AUTH_DENIES);
            }
        } else {
            throw new ReturnException(EmReturnError.USER_NOT_LOGIN);
        }
    }
}
