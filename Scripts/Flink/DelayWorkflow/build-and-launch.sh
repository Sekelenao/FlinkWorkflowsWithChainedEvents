#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Determine directories
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "${SCRIPT_DIR}/../../.." && pwd )"

CONTAINER_NAME="flink-jobmanager"
JAR_PATH="${PROJECT_ROOT}/DelayWorkflow/target/DelayWorkflow-1.0.0.jar"
CONTAINER_JAR_PATH="/tmp/DelayWorkflow.jar"
MAIN_CLASS="io.github.sekelenao.delay.Main"

echo "Checking if Flink JobManager container is running..."
if [ ! "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]; then
    echo "Error: The '${CONTAINER_NAME}' container is not running."
    echo "Please start the cluster first with: docker compose up -d"
    exit 1
fi

echo "Building the project..."
mvn -f "${PROJECT_ROOT}/pom.xml" clean package -pl DelayWorkflow -am

echo "Copying JAR to Flink JobManager container..."
docker cp "${JAR_PATH}" "${CONTAINER_NAME}:${CONTAINER_JAR_PATH}"

echo "Launching Flink stream job..."
docker exec ${CONTAINER_NAME} flink run -d -c ${MAIN_CLASS} ${CONTAINER_JAR_PATH}

echo "Success: Flink job submitted successfully!"
