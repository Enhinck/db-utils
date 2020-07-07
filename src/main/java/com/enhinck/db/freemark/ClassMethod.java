package com.enhinck.db.freemark;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-01 13:52
 */
@Data
public class ClassMethod {
    /**
     * 类注解
     */
    private List<String> annotations;
    /**
     * 类描述
     */
    private String classMethodDescribe;
    private String methodName;
    private String methodReturnType;
    private List<ClassField> methodParamTypes;
    private List<String> importList;

    /**
     * 方法内容
     */
    private List<String> codes;

    public List<String> getImportList() {

        if (importList == null) {
            importList = new ArrayList<>();
        }

        if (methodParamTypes != null) {
            methodParamTypes.forEach(methodParamType -> {
                if (methodParamType.getReference() != null) {
                    importList.add(methodParamType.getReference());
                }
            });
        }
        return importList;
    }
}
