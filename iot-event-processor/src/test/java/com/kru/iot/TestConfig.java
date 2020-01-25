package com.kru.iot;

import com.kru.iot.config.CassandraConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
public class TestConfig extends IotEventProcessorApplicationTests {

    @MockBean
    CassandraConfig cassandraConfig;

}
