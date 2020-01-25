CREATE KEYSPACE iot WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
USE iot;

// Custom Value Type to support generic value persistance

Create TYPE iot.value(
   dataType text,
   reading text
);

// Table for events

CREATE TABLE iot.events (
   deviceType text,
   deviceId text,
   eventTime timestamp,
   sensorReadings map<text, frozen<value>>,
   PRIMARY KEY (deviceType, deviceId, eventTime)
);

// Index to query Map
CREATE INDEX events_idx ON iot.events (entries(sensorreadings));


// UDF to handle UDA
CREATE OR REPLACE FUNCTION iot.getAsString(field text, data map<text, frozen<value>> )
    CALLED ON NULL INPUT
    RETURNS text
    LANGUAGE java
    AS $$
    
    UDTValue fieldValue  = data.get(field);
    String dataValue = fieldValue.getString(1);
    return dataValue;$$;



CREATE OR REPLACE FUNCTION iot.getAsLong(field text, data map<text, frozen<value>> )
    CALLED ON NULL INPUT
    RETURNS BIGINT
    LANGUAGE java
    AS $$
    String dataValue = null;
    if (data.containsKey(field)) {
        UDTValue fieldValue  = data.get(field);
        dataValue = fieldValue.getString(1);
    }
    return dataValue == null ? null : Long.valueOf(dataValue);$$;

CREATE OR REPLACE FUNCTION iot.getAsInteger(field text, data map<text, frozen<value>> )
    CALLED ON NULL INPUT
    RETURNS INT
    LANGUAGE java
    AS $$
    String dataValue = null;
    if (data.containsKey(field)) {
        UDTValue fieldValue  = data.get(field);
        dataValue = fieldValue.getString(1);
    }
    return dataValue == null ? null : Integer.valueOf(dataValue);$$;

CREATE OR REPLACE FUNCTION iot.getAsDouble(field text, data map<text, frozen<value>> )
    CALLED ON NULL INPUT
    RETURNS Double
    LANGUAGE java
    AS $$
    String dataValue = null;
    if (data.containsKey(field)) {
        UDTValue fieldValue  = data.get(field);
        dataValue = fieldValue.getString(1);
    }
    return dataValue == null ? null : Double.valueOf(dataValue);$$;
