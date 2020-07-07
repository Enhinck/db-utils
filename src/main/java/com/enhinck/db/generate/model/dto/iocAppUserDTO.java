package com.enhinck.db.generate.model.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 
 *
 * @author huenb
 * @date 2020-07-02 13:46:39
 */
@Data
public class iocAppUserDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 1男 2女
     */
    private Integer sex;

    /**
     * 头像照片
     */
    private String headImage;

    /**
     * 删除状态 0未删除 1删除
     */
    private Integer delFlag;

    public <T> T copyPropertiesTo(T target, String... ignoreProperties) {
        BeanUtils.copyProperties(this, target, ignoreProperties);
        return target;
    }
}