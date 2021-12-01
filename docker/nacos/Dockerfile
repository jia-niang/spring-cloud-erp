FROM amd64/buildpack-deps:buster-curl as installer

ARG NACOS_VERSION=2.0.3
ENV NV ${NACOS_VERSION}
RUN set -x \
    && curl -SL --output /var/tmp/nacos-server.tar.gz https://github.com/alibaba/nacos/releases/download/${NV}/nacos-server-${NV}.tar.gz \
    && tar -xzvf /var/tmp/nacos-server.tar.gz -C /home \
    && rm -rf /var/tmp/nacos-server.tar.gz /home/nacos/bin/* /home/nacos/conf/*

# base image
FROM openjdk:8-jre-slim

# set environment
ENV JAVA_HOME="/usr/local/openjdk-8" \
    BASE_DIR="/home/nacos" \
    MODE="standalone" \
    PREFER_HOST_MODE="ip"\
    CLASSPATH=".:/home/nacos/conf:$CLASSPATH" \
    CLUSTER_CONF="/home/nacos/conf/cluster.conf" \
    FUNCTION_MODE="all" \
    NACOS_USER="nacos" \
    NACOS_APPLICATION_PORT = "8848" \
    NACOS_DEBUG="n" \
    TOMCAT_ACCESSLOG_ENABLED="false" \
    TZ="Asia/Shanghai"

WORKDIR $BASE_DIR

# copy nacos jar
COPY --from=installer ["/home/nacos", "/home/nacos"]

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

EXPOSE $NACOS_APPLICATION_PORT
ENTRYPOINT ["bin/docker-startup.sh"]