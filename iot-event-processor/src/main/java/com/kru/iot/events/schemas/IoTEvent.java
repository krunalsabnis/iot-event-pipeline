package com.kru.iot.events.schemas;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kru.iot.config.DeviceEventDeserializer;
import com.kru.iot.repositories.EventEntity;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 */
@JsonDeserialize(using = DeviceEventDeserializer.class)
public interface IoTEvent {
    DeviceType getDeviceType();
    String getDeviceId();
    EventEntity convertToEventEntity();


}
