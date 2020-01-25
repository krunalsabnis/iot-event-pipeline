package com.kru.iot.events.processor.adapters;

import com.kru.iot.events.schemas.DeviceType;
import com.kru.iot.events.schemas.IoTEvent;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 */
public interface IoTEventAdapter {
    DeviceType getSupportedDeviceType();
    void processEvent(IoTEvent event);
}
