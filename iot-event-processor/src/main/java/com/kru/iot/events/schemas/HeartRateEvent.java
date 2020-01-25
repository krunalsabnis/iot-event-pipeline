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
@JsonDeserialize(as = HeartRateEvent.class)
public class HeartRateEvent implements IoTEvent {
    DeviceType deviceType;
    Integer heartRate;
    Date eventTime;
    String deviceId;


    @Override
    public DeviceType getDeviceType() {
        return DeviceType.HEARTRATE_METER;
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
        value.put("heartRate", EventEntity.Value.builder()
                .dataType("Integer")
                .reading(this.heartRate.toString())
                .build());
        return value;
    }
}
