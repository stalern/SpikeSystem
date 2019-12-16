package com.savannah.config;

import com.savannah.dataobject.UserInfoDO;
import com.savannah.error.ReturnException;
import com.savannah.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * @author stalern
 * @date 2019/12/10~09:41
 */

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private UserService userService;
    /**
     * 授权
     * @param principalCollection 权限集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     * @param authenticationToken 认证秘钥如密码
     * @return 认证信息
     * @throws AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String email = (String) authenticationToken.getPrincipal();
        System.out.println(authenticationToken.getCredentials());

        String pwd = null;
        try {
            pwd = userService.validateLogin(email);
        } catch (ReturnException e) {
            e.printStackTrace();
        }

        return new SimpleAuthenticationInfo(email,pwd,getName());
    }
}
