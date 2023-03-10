--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS calculations;
SET search_path TO calculations;

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS tag_name
(
    id             SERIAL PRIMARY KEY,
    permanent_name VARCHAR(256),
    name           VARCHAR(256),
    description    VARCHAR(256),
    initial        BOOLEAN,
    type           VARCHAR(32)
);

--changeset alina.parfenteva:3
CREATE TABLE IF NOT EXISTS report_name
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256) NOT NULL,
    creation_dt TIMESTAMP    NOT NULL,
    calc_method VARCHAR(32)  NOT NULL
);

--changeset alina.parfenteva:4
CREATE TABLE IF NOT EXISTS tag_data
(
    id             BIGINT PRIMARY KEY,
    data           VARCHAR(512) NOT NULL,
    tag_name_id    INTEGER      NOT NULL REFERENCES tag_name (id),
    report_name_id BIGINT       NOT NULL REFERENCES report_name (id)
);

CREATE SEQUENCE calc_tag_data_id_seq START 1 INCREMENT BY 50;


