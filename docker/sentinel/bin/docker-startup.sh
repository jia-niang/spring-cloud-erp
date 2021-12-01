#!/bin/bash
set -x
export SENTINEL_DIR="${BASE_DIR}"
#===========================================================================================
# JVM Configuration
#===========================================================================================
JAVA_OPT="${JAVA_OPT} -Xms512m -Xmx512m -Xmn256m"
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
JAVA_OPT="${JAVA_OPT} -jar ${SENTINEL_DIR}/sentinel-dashboard.jar"

exec "$JAVA $JAVA_OPT"

echo "sentinel is starting, you can docker logs your container"