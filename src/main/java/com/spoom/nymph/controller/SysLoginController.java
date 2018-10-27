package com.spoom.nymph.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spoom.nymph.entity.SysUserEntity;
import com.spoom.nymph.service.SysUserService;
import com.spoom.nymph.service.SysUserTokenService;
import com.spoom.nymph.utils.BaseResult;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.spoom.nymph.controller
 *
 * @author spoomlan
 * @date 2018/10/24
 */
@RestController
public class SysLoginController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @PostMapping("/sys/login")
    public BaseResult login(@RequestBody SysUserEntity userForm) throws Exception {
        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("username", userForm.getUsername()));
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(userForm.getPassword(), user.getSalt()).toHex())) {
            return BaseResult.error("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            return BaseResult.error("账号已被锁定,请联系管理员");
        }

        String token = sysUserTokenService.createToken(user.getUserId());

        return BaseResult.success().put("token", token).put("expire", 3600 * 12);
    }

    @PostMapping("/sys/logout")
    public BaseResult logout() {
        sysUserTokenService.logout(getUserId());
        return BaseResult.success();
    }
}
