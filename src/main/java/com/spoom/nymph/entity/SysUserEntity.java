package com.spoom.nymph.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

/**
 * package com.spoom.nymph.entity
 *
 * @author spoomlan
 * @date 2018/10/24
 */
@TableName("sys_user")
@Getter
@Setter
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = -1908807776643111621L;
    @TableId
    private Long userId;
    @Length(min = 6,max = 20,message = "请使用6至20位长度的用户名")
    private String username;
    @Length(min = 6,max = 50,message = "请使用6至50位长度的密码")
    private String password;
    private String salt;
    @Email(message = "邮箱格式错误")
    private String email;
    private String mobile;
    private Integer status;
    private Long createUserId;
    private Date createTime;
}
