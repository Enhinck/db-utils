#  db-utils 数据库比对升级脚本生成工具  v0.0.1

版本  | 说明
---|---
v0.0.1 | 初始版本


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

## 1. 打包
> mvn install

## 2. 配置 
#### 配置db.properties
> #目标数据库  
> new.db.url=jdbc:mysql://127.0.0.1:3306/newdb  
new.db.username=root             
new.db.password=mysql  
> #被升级数据库    
> old.db.url=jdbc:mysql://127.0.0.1:3306/olddb   
old.db.username=root  
old.db.password=mysql

## 3. 将db.properties与db-utils.jar放在同一目录下
执行
> java -jar db-utils.jar

将会在db-utils.jar所在目录下生成
> yyyy-MM-dd数据库更新脚本.sql

在olddb中执行该脚本，可将olddb表结构升级为newdb一致
"olddb"-->"newdb"



