package com.kru.iot.config;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.TransportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

/**
 * @author kru on 24-1-20
 * @project events-query-service
 */

@Slf4j
public class RetryingCassandraClusterFactoryBean extends CassandraClusterFactoryBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
    }

    private void connect() throws Exception {
        try {
            super.afterPropertiesSet();
        } catch (TransportException | IllegalArgumentException | NoHostAvailableException e) {
            log.warn(e.getMessage());
            log.warn("Retrying connection in 10 seconds");
            sleep();
            connect();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
    }
}
