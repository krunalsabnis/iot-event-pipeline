package com.kru.iot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * @author kru on 20-1-20
 * @project iot-event-processor
 */
@Configuration
@EnableCassandraRepositories//(basePackages = {"com.kru.iot.repositories"})
public class CassandraConfig extends AbstractCassandraConfiguration {

/*    public CassandraConfig() {
        CassandraClusterFactoryBean a = super.cluster();
        a.setJmxReportingEnabled(false);
    }*/
    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.data.cassandra.schema-action}")
    private String schemaAction;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

}