package com.spoom.nymph.controller;

import com.spoom.nymph.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * package com.spoom.nymph.controller
 *
 * @author spoomlan
 * @date 2018/10/24
 */
public abstract class AbstractController {
    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }
}
