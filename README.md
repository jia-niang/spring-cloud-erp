## 关于DO、DTO、BO、AO、VO、POJO定义
### 分层领域模型规约：

DO（ Data Object）：与数据库表结构一一对应，通过DAO层向上传输数据源对象。

DTO（ Data Transfer Object）：数据传输对象，Service或Manager向外传输的对象。

BO（ Business Object）：业务对象。 由Service层输出的封装业务逻辑的对象。

AO（ Application Object）：应用对象。 在Web层与Service层之间抽象的复用对象模型，极为贴近展示层，复用度不高。

VO（ View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。

POJO（ Plain Ordinary Java Object）：POJO专指只有setter/getter/toString的简单类，包括DO/DTO/BO/VO等。

Query：数据查询对象，各层接收上层的查询请求。 注意超过2个参数的查询封装，禁止使用Map类来传输。

### 领域模型命名规约：

数据对象：xxxDO，xxx即为数据表名。

数据传输对象：xxxDTO，xxx为业务领域相关的名称。

展示对象：xxxVO，xxx一般为网页名称。

POJO是DO/DTO/BO/VO的统称，禁止命名成xxxPOJO。

## 构建
```shell
mvn clean package
```
构建参数
```text
-P
P代表（Profiles配置文件） 在<profiles>指定的<id>中，可以通过-P进行传递或者赋值。 
打包时执行mvn clean package -P prod将触发prod环境的profile配置 
打包时执行mvn clean package -P test将触发test环境的profile配置
```

## 构建镜像
### 命令
```shell
mvn dockerfile:build 
```
### 注意
如果在构建镜像的使用需要打上环境相关的tag，需要带 `-P`参数，前提是你的配置中有设置相关参数。
## 发布镜像
```shell
mvn dockerfile:push
```