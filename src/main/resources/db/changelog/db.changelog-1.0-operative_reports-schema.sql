--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS operative_reports;

SET search_path TO operative_reports;

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS report_type
(
    id          VARCHAR(32) PRIMARY KEY,
    name        VARCHAR(256) NOT NULL,
    description VARCHAR(256),
    time_zone   INTEGER,
    active      BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS metering_node
(
    id   VARCHAR(32) PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    UNIQUE (name)
);

--changeset alina.parfenteva:3
CREATE TABLE IF NOT EXISTS report_row
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(256)                            NOT NULL,
    "order"        INTEGER                                 NOT NULL,
    report_type_id VARCHAR(32) REFERENCES report_type (id) NOT NULL
);


--changeset alina.parfenteva:4
CREATE TABLE IF NOT EXISTS tag_name
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(256)                              NOT NULL,
    description      VARCHAR(256),
    report_type_id   VARCHAR(32) REFERENCES report_type (id)   NOT NULL,
    row_id           INTEGER REFERENCES report_row (id)        NOT NULL,
    metering_node_id VARCHAR(32) REFERENCES metering_node (id) NOT NULL,
    UNIQUE (name, report_type_id)
);

--changeset alina.parfenteva:5
CREATE TABLE IF NOT EXISTS report_name
(
    id             BIGSERIAL PRIMARY KEY,
    report_type_id VARCHAR(32) REFERENCES report_type NOT NULL,
    name           VARCHAR(256)                       NOT NULL,
    creation_dt    TIMESTAMP                          NOT NULL,
    start_dt       TIMESTAMP                          NOT NULL,
    end_dt         TIMESTAMP                          NOT NULL
);

--changeset alina.parfenteva:6
CREATE TABLE IF NOT EXISTS tag_data
(
    id             BIGSERIAL PRIMARY KEY,
    data           DOUBLE PRECISION              NOT NULL,
    creation_dt    TIMESTAMP                     NOT NULL,
    tag_name_id    BIGINT REFERENCES tag_name    NOT NULL,
    report_name_id BIGINT REFERENCES report_name NOT NULL
);