--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS recurring_reports;

SET search_path TO recurring_reports;

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS report_type
(
    id          VARCHAR(32) PRIMARY KEY,
    name        VARCHAR(256),
    description VARCHAR(256),
    time_zone   INTEGER,
    active      BOOLEAN
);

--changeset alina.parfenteva:3
CREATE TABLE IF NOT EXISTS report_row
(
    id INTEGER PRIMARY KEY,
    name VARCHAR(256),
    "order" INTEGER,
    report_type_id VARCHAR(32) REFERENCES report_type (id)
);


--changeset alina.parfenteva:4
CREATE TABLE IF NOT EXISTS tag_name
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(256),
    description    VARCHAR(256),
    report_type_id VARCHAR(32) REFERENCES report_type (id),
    row_id         INTEGER REFERENCES report_row (id),
    UNIQUE (name, report_type_id)
);

--changeset alina.parfenteva:5
CREATE TABLE IF NOT EXISTS report_name
(
    id             BIGSERIAL PRIMARY KEY,
    report_type_id VARCHAR(32) REFERENCES report_type,
    name           VARCHAR(256),
    creation_dt    TIMESTAMP,
    start_dt       TIMESTAMP,
    end_dt         TIMESTAMP
);

--changeset alina.parfenteva:6
CREATE TABLE IF NOT EXISTS tag_data
(
    id             BIGSERIAL PRIMARY KEY,
    data           DOUBLE PRECISION,
    creation_dt    TIMESTAMP,
    tag_name_id    BIGINT REFERENCES tag_name,
    report_name_id BIGINT REFERENCES report_name
);
