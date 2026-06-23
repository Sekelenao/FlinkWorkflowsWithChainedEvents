#!/bin/bash

set -e

CONTAINER_NAME="flink-jobmanager"
JOB_NAME="DelayWorkflow"

echo "Checking if Flink JobManager container is running..."
if [ ! "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]; then
    echo "Error: The '${CONTAINER_NAME}' container is not running."
    exit 1
fi

echo "Searching for running Flink jobs named '${JOB_NAME}'..."
JOB_IDS=$(docker exec ${CONTAINER_NAME} flink list 2>/dev/null | grep "${JOB_NAME}" | grep "RUNNING" | awk -F ' : ' '{print $2}')

if [ -z "${JOB_IDS}" ]; then
    echo "No running Flink jobs named '${JOB_NAME}' were found."
    exit 0
fi

for JOB_ID in ${JOB_IDS}; do
    echo "Canceling Flink job ${JOB_ID}..."
    docker exec ${CONTAINER_NAME} flink cancel ${JOB_ID}
    echo "Success: Flink job ${JOB_ID} canceled."
done
