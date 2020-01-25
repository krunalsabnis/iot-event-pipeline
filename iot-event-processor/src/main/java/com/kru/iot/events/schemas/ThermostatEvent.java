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
@JsonDeserialize(as = ThermostatEvent.class)
public class ThermostatEvent implements IoTEvent {
    DeviceType deviceType;
    Double temperature;
    Long humidity;
    Date eventTime;
    String deviceId;


    @Override
    public DeviceType getDeviceType() {
        return DeviceType.THERMOSTAT;
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

        value.put("temperature", EventEntity.Value.builder()
                .dataType("Double")
                .reading(this.temperature.toString())
                .build());

        value.put("humidity", EventEntity.Value.builder()
                .dataType("Long")
                .reading(this.humidity.toString())
                .build());
        return value;
    }

}
