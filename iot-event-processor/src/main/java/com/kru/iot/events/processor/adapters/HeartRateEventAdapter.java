package com.kru.iot.events.processor.adapters;

import com.kru.iot.events.schemas.DeviceType;
import com.kru.iot.events.schemas.IoTEvent;
import com.kru.iot.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 */
@Component
@Slf4j
public class HeartRateEventAdapter implements IoTEventAdapter {
    private final EventRepository eventRepository;

    @Autowired
    public HeartRateEventAdapter(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @Override
    public DeviceType getSupportedDeviceType() {
        return DeviceType.HEARTRATE_METER;
    }

    @Override
    public void processEvent(IoTEvent event) {
        log.info("processing HEARTRATE_METER event {}", event);
        eventRepository.save(event.convertToEventEntity());

    }
}
