#!/bin/sh
CURR_DIR=$(basename $(pwd))
echo ":: Building ${CURR_DIR} project ::"
./mvnw clean package -DskipTests
