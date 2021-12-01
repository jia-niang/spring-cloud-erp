FROM amd64/buildpack-deps:buster-curl as installer

ARG SENTINEL_VERSION=1.8.2
ENV SV ${SENTINEL_VERSION}
# 直接下载jar包
RUN set -x \
    && curl -SL --output /var/tmp/sentinel/sentinel-dashboard.jar https://github.com/alibaba/Sentinel/releases/download/${SV}/sentinel-dashboard-${SV}.jar

# 基础镜像
FROM openjdk:8-jre-slim
# set environment
ENV JAVA_HOME="/usr/local/openjdk-8" \
    BASE_DIR="/var/www/sentinel" \
    SENTINEL_PORT="8849" \
    SENTINEL_USER="sentinel" \
    SENTINEL_PASSWORD="sentinel" \
    TZ="Asia/Shanghai"

WORKDIR $BASE_DIR

# copy sentinel jar
COPY --from=installer ["/var/tmp/sentinel", "/var/www/sentinel"]

# set startup log dir
RUN mkdir -p logs \
    && cd logs \
    && touch start.out \
    && ln -sf /dev/stdout start.out \
    && ln -sf /dev/stderr start.out

# startup
ADD bin/docker-startup.sh bin/docker-startup.sh
RUN chmod +x bin/docker-startup.sh

EXPOSE $SENTINEL_PORT
ENTRYPOINT ["bin/docker-startup.sh"]
