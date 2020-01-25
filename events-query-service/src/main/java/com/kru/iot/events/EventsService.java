package com.kru.iot.events;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.EndPoint;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.select.Selector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.select.Selector.column;


/**
 * @author kru on 20-1-20
 * @project events-query-service
 */
@Service
@Slf4j
public class EventsService {

    @Value("${config.cassandra.contact-points}")
    private String contactPoints;

    @Value("${config.cassandra.port}")
    private Integer port;

    @Value("${config.cassandra.keyspace-name:iot}")
    private String keyspaceName;

    @Value("${config.cassandra.events-table:events}")
    private String eventsTable;

    @Value("${config.cassandra.events-column:sensorreadings}")
    private String eventsColumn;

    @Autowired
    private SimpleDateFormat sdf;


    /**
     * Perform queries using QueryBuilder
     * A very basic implementation as dynamic queries on cassandra is not what it is known for.
     * Idea is to enhance this to build kind of ElasticSearch sort of REST API on Cassandra
     *
     * @param queryRequest
     * @return
     */
    public Row queryEvents(Query queryRequest) {
        ResultSet rs = null;
        Row row = null;
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("iot")
                .build()) {
            Select query = selectFrom(eventsTable)

                    .function(queryRequest.getAggregateFunction(),
                            Selector.function(keyspaceName, "\"getasdouble\"",
                                    QueryBuilder.literal(queryRequest.getField()), column(eventsColumn)))
                    .as(queryRequest.getAggregateFunction() + "_OF_" + queryRequest.getField())
                    .whereColumn("deviceType").isEqualTo(QueryBuilder.literal(queryRequest.getDeviceType()))
                    .whereColumn("deviceId").isEqualTo(QueryBuilder.literal(queryRequest.getDeviceId()))
                    .whereColumn("eventTime").isGreaterThanOrEqualTo(QueryBuilder.literal(sdf.format(queryRequest.getFromDate())))
                    .whereColumn("eventTime").isLessThanOrEqualTo(QueryBuilder.literal(sdf.format(queryRequest.getToDate())))
                    .allowFiltering();

            if (queryRequest.getGroupBy() != null) {
                query = query.groupBy(queryRequest.getGroupBy());
            }

            SimpleStatement statement = query.build();
            log.info("executing statement {} ", statement);

            row = session.execute(statement).one();

        } catch ( Exception e) {
            log.error("Error {}", e);
        }
        return row;
    }
}
