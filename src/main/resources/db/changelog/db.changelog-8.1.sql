--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER TABLE poverka.tag_data
    RENAME COLUMN manual_tag_name_id TO tag_name_id;
ALTER TABLE poverka.tag_data
    RENAME COLUMN report_name_poverka_id TO report_name_id;


