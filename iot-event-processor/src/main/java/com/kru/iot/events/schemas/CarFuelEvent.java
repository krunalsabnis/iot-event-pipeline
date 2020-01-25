package com.kru.iot.events.schemas;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kru.iot.repositories.EventEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonDeserialize(as = CarFuelEvent.class)
public class CarFuelEvent implements IoTEvent {
    DeviceType deviceType;
    Double fuelLevel;
    Date eventTime;
    String deviceId;


    @Override
    public DeviceType getDeviceType() {
        return DeviceType.CAR_FUEL;
    }

    @Override
    public String getDeviceId() {
        return this.deviceId;
    }

    @Override
    public EventEntity convertToEventEntity() {

        return EventEntity.builder()
                .deviceId(this.deviceId)
                .deviceType(this.deviceType)
                .eventTime(this.eventTime)
                .sensorReadings(getValueMap())
                .build();

    }

    private Map<String, EventEntity.Value> getValueMap() {
        Map<String, EventEntity.Value> value = new HashMap<>();

        value.put("fuelLevel", EventEntity.Value.builder()
                .dataType("Double")
                .reading(this.fuelLevel.toString())
                .build());
        return value;
    }
}
