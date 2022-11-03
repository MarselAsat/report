--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE TABLE IF NOT EXISTS report_name_calc
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(256) NOT NULL,
    creation_dt  TIMESTAMP    NOT NULL,
    calc_method VARCHAR(32)  NOT NULL
);

--changeset alina.parfenteva:2
-- noinspection SqlResolve
CREATE TABLE IF NOT EXISTS tag_data_calc3622
(
    id                     BIGINT PRIMARY KEY,
    data                   VARCHAR(512)                               NOT NULL,
    data_type              VARCHAR(32)                                NOT NULL,
    manual_tag_name_id     INT REFERENCES manual_tag_name (id)        NOT NULL,
    report_name_calc_id BIGINT REFERENCES report_name_calc (id) NOT NULL
);

CREATE SEQUENCE tag_data_calc3622_seq START 1 INCREMENT 50;