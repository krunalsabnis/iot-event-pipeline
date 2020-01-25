package com.kru.iot.events.simulator;

import com.kru.iot.events.schemas.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 * Simulates events from various types of events
 * Mock events are generated and sent to Kafka Topic
 */
@Service
@Slf4j
public class SimulatorService {
    private final KafkaTemplate<String, IoTEvent> kafkaTemplate;

    private final String targetTopic;

    private final Random random;

    @Autowired
    public SimulatorService(KafkaTemplate<String, IoTEvent> kafkaTemplate, @Value(value = "${kafka.events.topic}") String targetTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.targetTopic = targetTopic;
        this.random = new Random();
    }

    @Scheduled(cron = "*/10 * * * * *") // = every 10 second
    public void publishMockEvents() {
        kafkaTemplate.send(targetTopic, getRandomThermostatEvent());
        kafkaTemplate.send(targetTopic, getRandomHeartRateEvent());
        kafkaTemplate.send(targetTopic, getRandomCarFuelEvent());
    }

    private ThermostatEvent getRandomThermostatEvent() {
        return ThermostatEvent.builder()
                .eventTime(new Date())
                .deviceId(getRandomDeviceId("TH"))
                .deviceType(DeviceType.THERMOSTAT)
                .temperature(ThreadLocalRandom.current().nextDouble(35.00, 100.00))
                .humidity(ThreadLocalRandom.current().nextLong(10000L, 10000000L))
                .build();
    }

    private HeartRateEvent getRandomHeartRateEvent() {
        return HeartRateEvent.builder()
                .eventTime(new Date())
                .deviceId(getRandomDeviceId("HR"))
                .deviceType(DeviceType.HEARTRATE_METER)
                .heartRate(ThreadLocalRandom.current().nextInt(1,180))
                .build();

    }

    private CarFuelEvent getRandomCarFuelEvent() {
        return CarFuelEvent.builder()
                .eventTime(new Date())
                .deviceId(getRandomDeviceId("FUEL"))
                .deviceType(DeviceType.CAR_FUEL)
                .fuelLevel(ThreadLocalRandom.current().nextDouble(1.00, 35.00))
                .build();
    }


    /**
     * Generates random Integer from 1000 to 1010
     * Keeping low range to have more repeating deviceIds
     * @return
     */
    private String getRandomDeviceId(String deviceIdPrefix) {
        return deviceIdPrefix +  "_" + ThreadLocalRandom.current().nextInt(1000, 1010);
    }
}
