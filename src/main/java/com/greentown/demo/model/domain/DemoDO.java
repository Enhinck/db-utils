package com.greentown.demo.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import com.greentown.mybatisplus.domain.MyBatisPlusBaseDO;

/**
 * 案例
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoDO extends MyBatisPlusBaseDO<DemoDO> {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 删除状态 0 正常 1删除
     */
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;
}