package com.spoom.nymph.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * package com.spoom.nymph.entity
 *
 * @author spoomlan
 * @date 2018/10/24
 */
@TableName("sys_user_token")
@Getter
@Setter
public class SysUserTokenEntity implements Serializable {
    private static final long serialVersionUID = -1240053269497599183L;
    @TableId(type = IdType.INPUT)
    private Long userId;

    private String token;
    private Date expireTime;
    private Date updateTime;
}
