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