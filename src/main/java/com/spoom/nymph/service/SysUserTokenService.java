package com.spoom.nymph.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spoom.nymph.entity.SysUserTokenEntity;

/**
 * package com.spoom.nymph.service
 *
 * @author spoomlan
 * @date 2018/10/26
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return
     */
    String createToken(Long userId);

    /**
     * 退出登录，修改token值
     *
     * @param userId 用户ID
     */
    void logout(Long userId);
}
