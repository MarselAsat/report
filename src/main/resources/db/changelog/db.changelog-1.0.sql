--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE TABLE IF NOT EXISTS report_type
(
    id          VARCHAR(32) PRIMARY KEY,
    name        VARCHAR(256),
    description VARCHAR(256),
    time_zone   INT,
    active      BOOLEAN
);

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS tag_name
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(256),
    description    VARCHAR(256),
    report_type_id VARCHAR(32) REFERENCES report_type (id),
    UNIQUE (name, report_type_id)
);

--changeset alina.parfenteva:3
CREATE TABLE IF NOT EXISTS manual_tag_name
(
    id             SERIAL PRIMARY KEY,
    permanent_name VARCHAR(256),
    name           VARCHAR(256),
    description    VARCHAR(256),
    initial        BOOLEAN,
    type           VARCHAR(32)
);

--changeset alina.parfenteva:4
CREATE TABLE IF NOT EXISTS report_name
(
    id             BIGSERIAL PRIMARY KEY,
    report_type_id VARCHAR(32) REFERENCES report_type (id),
    name           VARCHAR(256),
    date_creation  TIMESTAMP
);

--changeset alina.parfenteva:5
CREATE TABLE IF NOT EXISTS tag_data
(
    id             BIGSERIAL PRIMARY KEY,
    data           NUMERIC,
    date_creation  TIMESTAMP,
    tag_name_id    BIGINT REFERENCES tag_name (id),
    report_name_id BIGINT REFERENCES report_name (id)
);

--changeset alina.parfenteva:6
CREATE TABLE IF NOT EXISTS text_tag_data
(
    id                 BIGSERIAL PRIMARY KEY,
    data               VARCHAR(256),
    date_creation      TIMESTAMP,
    manual_tag_name_id BIGINT REFERENCES manual_tag_name (id),
    report_name_id     BIGINT REFERENCES report_name (id)
);

--changeset alina.parfenteva:7
CREATE TABLE IF NOT EXISTS "user"
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(256),
    password VARCHAR(256),
    role     VARCHAR(256)
);