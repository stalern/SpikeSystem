package com.savannah.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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

        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            String loginUser = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
            if (loginUser.equals(loginUser)){
                session.setTimeout(0);
                break;
            }
        }

        return new SimpleAuthenticationInfo();
    }
}
