package com.enhinck.db.freemark;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 泛型
 *
 * @author huenbin
 * @date 2020-07-01 11:07
 */
@Data
@NoArgsConstructor
public class ClassGenerics {
    private String className;
    private String reference;

    public ClassGenerics(String className) {
        this.className = className;
    }
}
