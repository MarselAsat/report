--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER TABLE tag_data
    RENAME COLUMN date_creation TO creation_dt;

--changeset alina.parfenteva:2
ALTER TABLE text_tag_data
    RENAME COLUMN date_creation TO creation_dt;