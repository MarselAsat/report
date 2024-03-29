--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS manual_reports;

SET search_path TO manual_reports;

CREATE TABLE IF NOT EXISTS report_type
(
    id          VARCHAR(32) PRIMARY KEY,
    name        VARCHAR(256) NOT NULL,
    description VARCHAR(256),
    active      BOOLEAN      NOT NULL
);

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS tag
(
    id             SERIAL PRIMARY KEY,
    permanent_name VARCHAR(256) NOT NULL,
    address        VARCHAR(256) NOT NULL UNIQUE CHECK (address <> ''),
    description    VARCHAR(512),
    initial        BOOLEAN      NOT NULL,
    report_type_id   VARCHAR(32) REFERENCES report_type (id)   NOT NULL,
    UNIQUE(permanent_name, report_type_id, initial)
);

--changeset alina.parfenteva:3
CREATE TABLE IF NOT EXISTS report
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256) NOT NULL,
    creation_dt TIMESTAMP    NOT NULL,
    report_type_id   VARCHAR(32) REFERENCES report_type (id)   NOT NULL
);

--changeset alina.parfenteva:4
CREATE TABLE IF NOT EXISTS report_data
(
    id        BIGINT PRIMARY KEY,
    data      VARCHAR(1024) NOT NULL,
    tag_id    INTEGER      NOT NULL REFERENCES tag (id),
    report_id BIGINT       NOT NULL REFERENCES report (id)
);

CREATE SEQUENCE manual_report_data_id_seq START 1 INCREMENT BY 50;


