package ${packagePath};

<#if importList??>
<#list importList as item>
import ${item};
</#list>
</#if>

/**
 * ${classDescribe!}
 *
 * @author enhinck Generate auto created
 * @date ${createDateTime!}
 */
<#if annotations??>
  <#list annotations as item>
${item}
  </#list>
</#if>
public ${classType} ${className} <#if extendsClass??>extends ${extendsClass}</#if><#if extendsGenerics??>${r"<"}<#list extendsGenerics as extendsGeneric>${extendsGeneric.className}<#if extendsGeneric_has_next>, </#if></#list>${r">"}</#if><#if implementsInterfaces??> implements <#list implementsInterfaces as implementsInterface>${implementsInterface}<#if implementsInterface_has_next>, </#if></#list></#if> {
<#if fields??>
    <#list fields as item>
    <#if item.classFieldDescribe??>

    /**
     * ${item.classFieldDescribe!}
     */
    </#if>
      <#if item.annotations??>
        <#list item.annotations as annontation>
    ${annontation}
        </#list>
      </#if>
    private <#if item.classModify??>${item.classModify} </#if>${item.fieldType} ${item.fieldName};
    </#list>
</#if>
<#if methods??>
<#list methods as item>

    /**
     * ${item.classMethodDescribe!}
     *
     <#if item.methodParamTypes??>
     <#list item.methodParamTypes as methodParamType>
     * @param ${methodParamType.fieldName} ${methodParamType.classFieldDescribe!}
     </#list>
     </#if>
     <#if item.methodReturnType=="void">
     *
     <#else >
     * @return
     </#if>
     */
<#if item.annotations??>
<#list item.annotations as annontation>
    ${annontation}
</#list>
</#if>
    <#if item.codes??>
    public ${item.methodReturnType} ${item.methodName}(<#if item.methodParamTypes??><#list item.methodParamTypes as methodParamType>${methodParamType.fieldType} ${methodParamType.fieldName}<#if methodParamType_has_next>, </#if></#list></#if>) {
    <#list item.codes as code>
        ${code}
    </#list>
    }
    <#else >
    public ${item.methodReturnType} ${item.methodName} (<#if item.methodParamTypes??><#list item.methodParamTypes as methodParamType>${methodParamType.fieldType} ${methodParamType.fieldName}<#if methodParamType_has_next>, </#if></#list></#if>);
    </#if>
</#list>
</#if>
}