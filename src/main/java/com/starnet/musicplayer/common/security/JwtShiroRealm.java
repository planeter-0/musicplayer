package com.starnet.musicplayer.common.security;


import com.starnet.musicplayer.model.entity.UserEntity;
import com.starnet.musicplayer.service.UserService;
import com.starnet.musicplayer.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class JwtShiroRealm extends AuthorizingRealm {
    @Resource
    UserService userService;

    // 设置Matcher
    public JwtShiroRealm() {
        this.setCredentialsMatcher(new JwtCredentialsMatcher());
    }

    /**
     * 设置支持的token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * JWTRealm只负责登陆后的请求认证
     *
     * @param principals PrincipalCollection
     * @return SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authToken;
        String token = jwtToken.getToken();
        UserEntity user = userService.getByUsername(JwtUtils.getUsername(token));
        if (user == null)
            throw new AuthenticationException("token过期，请重新登录");
        return new SimpleAuthenticationInfo(
                user, //principal
                token, //credential
                getName());
    }
}
