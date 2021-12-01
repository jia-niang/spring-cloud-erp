# base image
FROM openjdk:8-jre-slim

# set environment
ENV JAVA_HOME="/usr/local/openjdk-8" \
    BASE_DIR="/home/nacos" \
    CLASSPATH=".:/home/nacos/conf:$CLASSPATH" \
    MODE="standalone" \
    PREFER_HOST_MODE="ip"\
    FUNCTION_MODE="all" \
    NACOS_USER="nacos" \
    NACOS_APPLICATION_PORT="8848" \
    NACOS_DEBUG="n" \
    TOMCAT_ACCESSLOG_ENABLED="false" \
    TZ="Asia/Shanghai"

WORKDIR $BASE_DIR

# copy nacos jar
COPY target/nacos-server.jar target/nacos-server.jar
#
ADD bin/docker-startup.sh bin/docker-startup.sh
ADD conf/application.properties conf/application.properties
ADD conf/nacos-logback.xml conf/nacos-logback.xml

# set startup log dir
RUN mkdir -p logs \
    && cd logs \
    && touch start.out \
    && ln -sf /dev/stdout start.out \
    && ln -sf /dev/stderr start.out
RUN chmod +x bin/docker-startup.sh

EXPOSE 8848
ENTRYPOINT ["bin/docker-startup.sh"]