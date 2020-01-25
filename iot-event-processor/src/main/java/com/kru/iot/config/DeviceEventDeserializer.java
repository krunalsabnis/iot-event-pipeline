package com.kru.iot.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kru.iot.events.schemas.*;

import java.io.IOException;


public class DeviceEventDeserializer extends JsonDeserializer<IoTEvent> {


    @Override
    public IoTEvent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ObjectNode root = mapper.readTree(p);

        if (root.has("deviceType") && root.get("deviceType").asText().equals(DeviceType.THERMOSTAT.name())) {
            return mapper.readValue(root.toString(), ThermostatEvent.class);
        } else if (root.has("deviceType") && root.get("deviceType").asText().equals(DeviceType.HEARTRATE_METER.name())) {
            return mapper.readValue(root.toString(), HeartRateEvent.class);
        } else if (root.has("deviceType") && root.get("deviceType").asText().equals(DeviceType.CAR_FUEL.name())) {
            return mapper.readValue(root.toString(), CarFuelEvent.class);
        } else {
            throw new RuntimeException("handler not registered for this event");
        }
    }
}
