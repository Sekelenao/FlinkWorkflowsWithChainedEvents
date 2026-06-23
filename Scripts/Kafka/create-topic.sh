#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Define variables
CONTAINER_NAME="kafka"
TOPIC_NAME="chained-events"
PARTITIONS=1
REPLICATION_FACTOR=1

echo "Checking if Kafka container is running..."
if [ "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]; then
    echo "Creating Kafka topic '${TOPIC_NAME}'..."
    docker exec ${CONTAINER_NAME} /opt/kafka/bin/kafka-topics.sh \
      --bootstrap-server localhost:9092 \
      --create \
      --topic ${TOPIC_NAME} \
      --partitions ${PARTITIONS} \
      --replication-factor ${REPLICATION_FACTOR} \
      --if-not-exists
    echo "Success: Topic '${TOPIC_NAME}' created or already exists!"
else
    echo "Error: The '${CONTAINER_NAME}' container is not running."
    echo "Please start the cluster first with: docker compose up -d"
    exit 1
fi
