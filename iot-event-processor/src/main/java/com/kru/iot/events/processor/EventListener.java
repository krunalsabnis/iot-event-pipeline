package com.kru.iot.events.processor;

import com.kru.iot.events.schemas.IoTEvent;
import com.kru.iot.events.processor.adapters.IoTEventAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author kru on 19-1-20
 * @project iot-event-processor
 */
@Service
@Slf4j
public class EventListener {

    private final IoTEventAdapterService ioTEventAdapterService;

    @Autowired
    public EventListener(IoTEventAdapterService ioTEventAdapterService) {
        this.ioTEventAdapterService = ioTEventAdapterService;
    }
    @KafkaListener(topics = "device-events", groupId = "foo")
    public void listen(IoTEvent message) {
        log.info("received Message: {} ", message);
        ioTEventAdapterService.process(message);
    }
}
