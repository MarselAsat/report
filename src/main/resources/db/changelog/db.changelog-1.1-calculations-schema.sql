--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS calculations;
SET search_path TO calculations;

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS tag
(
    id             SERIAL PRIMARY KEY,
    permanent_name VARCHAR(256) NOT NULL UNIQUE,
    address        VARCHAR(256) NOT NULL UNIQUE CHECK (address <> ''),
    description    VARCHAR(512),
    initial        BOOLEAN      NOT NULL,
    calc_method    VARCHAR(32)  NOT NULL
);

--changeset alina.parfenteva:3
CREATE TABLE IF NOT EXISTS report
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256) NOT NULL,
    creation_dt TIMESTAMP    NOT NULL,
    calc_method VARCHAR(32)  NOT NULL
);

--changeset alina.parfenteva:4
CREATE TABLE IF NOT EXISTS report_data
(
    id        BIGINT PRIMARY KEY,
    data      VARCHAR(512) NOT NULL,
    tag_id    INTEGER      NOT NULL REFERENCES tag (id),
    report_id BIGINT       NOT NULL REFERENCES report (id)
);

CREATE SEQUENCE calc_report_data_id_seq START 1 INCREMENT BY 50;


