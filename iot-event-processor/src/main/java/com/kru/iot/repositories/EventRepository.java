package com.kru.iot.repositories;

import com.kru.iot.events.schemas.DeviceType;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kru on 20-1-20
 * @project iot-event-processor
 */
@Repository
public interface EventRepository extends CassandraRepository<EventEntity, DeviceType> {
}
