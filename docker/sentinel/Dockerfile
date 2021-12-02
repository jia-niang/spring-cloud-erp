# 基础镜像
FROM openjdk:8-jre-slim
LABEL maintainer="jonathan<jianyang1209@outloook.com>"
# set environment
ENV BASE_DIR="/home/sentinel" \
    SENTINEL_PORT="8849" \
    SENTINEL_USER="sentinel" \
    SENTINEL_PASSWORD="sentinel" \
    TZ="Asia/Shanghai"

WORKDIR $BASE_DIR

# copy sentinel jar
COPY target/sentinel-dashboard-1.8.2.jar target/sentinel-dashboard.jar

# set startup log dir
RUN mkdir -p logs \
    && cd logs \
    && touch start.out \
    && ln -sf /dev/stdout start.out \
    && ln -sf /dev/stderr start.out

# startup
ADD bin/docker-startup.sh bin/docker-startup.sh
RUN chmod +x bin/docker-startup.sh

EXPOSE 8849
ENTRYPOINT ["bin/docker-startup.sh"]