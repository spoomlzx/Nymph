package com.spoom.nymph.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spoom.nymph.config.shiro.TokenGenerator;
import com.spoom.nymph.dao.SysUserTokenDao;
import com.spoom.nymph.entity.SysUserTokenEntity;
import com.spoom.nymph.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * package com.spoom.nymph.service.impl
 *
 * @author spoomlan
 * @date 2018/10/26
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //token过期时间，12小时
    private final static int EXPIRE = 3600 * 12;

    @Override
    public String createToken(Long userId) {
        String token = TokenGenerator.generateValue();
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断DB中是否有生成过的token
        SysUserTokenEntity tokenEntity = baseMapper.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            baseMapper.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            baseMapper.updateById(tokenEntity);
        }
        return token;
    }

    @Override
    public void logout(Long userId) {
        String token = TokenGenerator.generateValue();
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        baseMapper.updateById(tokenEntity);
    }
}
