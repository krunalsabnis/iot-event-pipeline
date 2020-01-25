#!/bin/bash

BASE_PATH=`pwd`
echo "building event-processor service"
cd $BASE_PATH/iot-event-processor
./gradlew clean build


echo "building event-query-service"
cd $BASE_PATH/events-query-service
./gradlew clean build


sleep 5
echo "\n\n\n Bringing Stack Up .. ."

docker-compose up -d

