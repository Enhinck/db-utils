#  db-utils 数据库比对升级脚本生成工具  v0.0.1

版本  | 说明
---|---
v0.0.1 | 初始版本
v0.0.2 | 增加数据字典生成
v0.0.3 | 增加数据库数据比对升级功能


```
v0.0.1 功能简介
数据库比对升级脚本生成工具
新增的表
新增的字段
修改的字段(不支持字段编码修改)
删除的字段（这里不生成DROP脚本,对数据有影响故不生成）
```

```
v0.0.2 功能简介
MysqlDbToDictionary 
数据库表结构生成excel版数据字典以及doc版数据字典
```

```
v0.0.3 功能简介
MysqlDbCompare 
在0.0.1版本基础上增加数据库数据比对功能
```

## 1. 打包
> mvn install

## 2. 配置 
#### 配置db.properties
> \#新版本数据库 (旧版本升级为新版本) 
new.db.url=jdbc:mysql://127.0.0.1:3306/newdb  
new.db.username=root             
new.db.password=mysql  
\# 旧版本数据库  
old.db.url=jdbc:mysql://127.0.0.1:3306/olddb   
old.db.username=root\
old.db.password=mysql\
\#需要同步数据的表\
db.datasync.tables=table_name1,table_name2\
\#文档生成路径 空值为当前路径\
doc.path=

## 3. 将db.properties与db-utils.jar放在同一目录下
执行
> java -jar db-utils.jar

将会在db-utils.jar所在目录下生成
> 数据库更新脚本yyyy-MM-dd_HH_mm_ss.sql

在olddb中执行该脚本，可将olddb表结构升级为newdb一致
"olddb"-->"newdb"
- 0.0.3版本中增加同步表功能，可将同步列表中的olddb数据也升级到newdb(只做增量和更新操作)

如需使用数据字典生成功能改变pom.xml中的mainClass为com.enhinck.db.MysqlDbToDictionary重新打包出数据字典生成工具即可
```
<manifest>
	<!-- 此处指定main方法入口的class -->
	<mainClass>com.enhinck.db.MysqlDbCompare</mainClass>
</manifest>
```



