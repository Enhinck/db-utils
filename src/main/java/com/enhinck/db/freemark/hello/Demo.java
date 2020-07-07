package com.enhinck.db.freemark.hello;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 案例
 *
 * @author enhinck Generate auto created
 */
@Data
public class Demo implements IService {
    /**
     * 姓名
     */
    @TableId(type = IdType.AUTO)
    private String name;
    /**
     * 性别
     */
    private Integer sex;

    /**
     * 测试方法
     *
     * @param string 字符串
     * @return
     */
    public String test(String string) {
        System.out.print(string);
        return string;
    }
}