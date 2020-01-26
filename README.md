# IoT Event Pipeline


This is a Event Processing Pipeline for Events.


## 1. Cassandra

Events are stored in Cassandra. 
The directory 'cassandra' includes customized docker file which applies certain changes to cassandra database in order to have appropriate data model.
We need User Defined Type called VALUE and set of User Defined Functions to handled VALUE.

## 2. iot-event-processor

IoT Event Processor is a Java Worker service written in Spring Boot.
For demo / test purpose it produces mock events and push to Kafka Stream. Main objective of this service to consume Kafka Stream and process the events.
This service uses, Adapter & Handler pattern to identify and handle events appropriately. Which makes it easy to enhance to support new type of events.

## 3. events-query-service

This is Spring Boot REST Service which exposes single generic API endpoint to peroform standard Query on any event type, by timerange including aggregate functions supported by Cassandra.



## Build and Run

### Prerequisites
* Java JDK 8
* Docker

Whole stack is containarized to make it easy to run locally for test / debug & development purpose

To bring whole stack up simply clone this repository and run below shell script

[ See below for Windows ]
```
> ./build-run.sh

```


It buils both java serivces and brings whole docker cluster up using docker compose 


It brings Kafka, Zookeeper, Cassandra, Portainer, and both Java services - hence total 6 containers up.
It may fail in case below ports are already in use in your local machine

Ports: 
	8080: For REST API's of Events  Query Service
        9000: For monitoring portianer container
        2181: Zookeeper
        9042: Cassandra


Windows:

Execute below commands to build and run whole stack locally

```
// Build event processor

cd iot-event-processor
gradlew clean build

// build rest service

cd ../events-query-service
gradlew clean build

// bring docker containers up
docker-compose up -d


* Monitor using Portainer on loclhost:9000
* Swagger page on localhost:8080/swagger-ui.html

APIs expects Authentication Token hence pass "Bearer 1234" in Authorization. (As of now Token is hardcoded, but have handles which ideally should do JWT token verification)


![Alt text](/images/portainer.png?raw=true "Portainer while running all containers")
![Alt text](/images/docker-stack.png?raw=true "Docker Stack for Local run")
![Alt text](/images/diagram.png?raw=true "High Level Architecture")







