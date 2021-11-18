FROM openjdk:8-jre-slim
LABEL maintainer="jonathan <jianyang1209@outlook.com>"
# 工作目录
WORKDIR /spring
# 添加已构建的jar包
ARG JAR_FILE
ADD target/${JAR_FILE} im.jar
# 上海时区
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone
# 向外暴露的端口号
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "im.jar"]