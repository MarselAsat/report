--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER TABLE report_name
    ADD COLUMN start_dt TIMESTAMP,
    ADD COLUMN end_dt   TIMESTAMP;

--changeset alina.parfenteva:2
ALTER TABLE report_name
    RENAME COLUMN date_creation TO creation_dt;