package com.greentown.demo.model.vo;

import com.greentown.common.model.vo.BaseVO;
import io.swagger.annotations.*;
import lombok.*;

/**
 * 案例
 *
 * @author enhinck Generate auto created
 * @date 2020-07-07 19:33:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("案例")
public class DemoVO extends BaseVO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "删除状态 0 正常 1删除")
    private Integer delFlag;
}