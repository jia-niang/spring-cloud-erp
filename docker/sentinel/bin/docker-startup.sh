#!/bin/bash
set -x
export SENTINEL_DIR="${BASE_DIR}"
export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
#===========================================================================================
# JVM Configuration
#===========================================================================================
JAVA_OPT="-Xms512m -Xmx512m -Xmn256m"
#===========================================================================================
# Setting system properties
#===========================================================================================
# set sentinel server port
JAVA_OPT="${JAVA_OPT} -Dserver.port=${SENTINEL_PORT}"
JAVA_OPT="${JAVA_OPT} -Dcsp.sentinel.dashboard.server=localhost:${SENTINEL_PORT}"
JAVA_OPT="${JAVA_OPT} -Dproject.name=sentinel-dashboard"
# username and password
JAVA_OPT="${JAVA_OPT} -Dsentinel.dashboard.auth.username=${SENTINEL_USER}"
JAVA_OPT="${JAVA_OPT} -Dsentinel.dashboard.auth.password=${SENTINEL_PASSWORD}"
# jar
JAVA_OPT="${JAVA_OPT} -jar ${SENTINEL_DIR}/target/sentinel-dashboard.jar"

echo "sentinel is starting, you can docker logs your container"

# shellcheck disable=SC2086
exec "$JAVA" $JAVA_OPT