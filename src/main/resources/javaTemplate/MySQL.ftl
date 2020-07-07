<#if tables??>
  <#list tables as table>
--${table.describe}
CREATE TABLE `${table.tableName}` (
  <#if table.list??>
  <#list table.list as column>
  `${column.dbColumnName()}`  ${column.dbType()} <#if column.dbColumnName() == "id"> NOT NULL AUTO_INCREMENT<#else> DEFAULT NULL </#if> COMMENT '${column.describe}',
  </#list>
  </#if>
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
  </#list>
</#if>