package com.kru.iot.events.processor.adapters;

import com.kru.iot.events.schemas.DeviceType;
import com.kru.iot.events.schemas.IoTEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 */
@Service
@Slf4j
public class IoTEventAdapterService {
    private final Map<DeviceType, IoTEventAdapter> deviceAdapters = new HashMap<>();
    IoTEventAdapterService(List<IoTEventAdapter> eventAdapters) {
        for (IoTEventAdapter deviceFactory : eventAdapters) {
            deviceAdapters.put(deviceFactory.getSupportedDeviceType(), deviceFactory);
        }

        log.info("Initialized device event adapter service with {}", deviceAdapters);
    }

    public void process(IoTEvent event) {
        IoTEventAdapter adapter = getAdapter(event);
        adapter.processEvent(event);
    }

    private IoTEventAdapter getAdapter(IoTEvent event) {
        if (deviceAdapters.containsKey(event.getDeviceType()))
            return deviceAdapters.get(event.getDeviceType());
        else
            log.warn("Unknown Event Type {}", event.getDeviceType());
        return null;
    }

}
