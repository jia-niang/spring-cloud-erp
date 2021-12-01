ARG MYSQL_VERSION=latest
FROM mysql:${MYSQL_VERSION}
LABEL maintainer="jonathan<jianyang1209@outloook.com>"
#####################################
# Set Timezone
#####################################
ARG MYSQL_TIMEZONE=UTC
RUN ln -snf /usr/share/zoneinfo/${MYSQL_TIMEZONE} /etc/localtime && echo ${MYSQL_TIMEZONE} > /etc/timezone && chown -R mysql:root /var/lib/mysql/
COPY ./my.cnf /etc/mysql/conf.d/my.cnf
RUN chmod 0444 /etc/mysql/conf.d/my.cnf