package com.spoom.nymph.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spoom.nymph.entity.SysUserEntity;
import com.spoom.nymph.entity.SysUserTokenEntity;
import com.spoom.nymph.service.SysUserService;
import com.spoom.nymph.service.SysUserTokenService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * package com.spoom.nymph.config.shiro
 *
 * @author spoomlan
 * @date 2018/10/24
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        //查询token对应的userEntity
        SysUserTokenEntity tokenEntity = sysUserTokenService.getOne(new QueryWrapper<SysUserTokenEntity>().eq("token", accessToken));
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("user_id", tokenEntity.getUserId()));
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
