package com.spoom.nymph.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spoom.nymph.dao.SysUserDao;
import com.spoom.nymph.entity.SysUserEntity;
import com.spoom.nymph.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * package com.spoom.nymph.service.impl
 *
 * @author spoomlan
 * @date 2018/10/24
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
}
