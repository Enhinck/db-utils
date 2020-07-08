package com.greentown.demo.model.dto;

import com.greentown.common.model.dto.BaseDTO;
import io.swagger.annotations.*;
import lombok.*;

/**
 * 测试
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("测试")
public class TestDTO extends BaseDTO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "删除状态 0 正常 1删除")
    private Integer delFlag;
}