--liquibase formatted sql

--changeset alina.parfenteva:1
-- noinspection SqlResolve
ALTER TABLE calculations.tag_data
    RENAME COLUMN manual_tag_name_id TO tag_name_id;
-- noinspection SqlResolve
ALTER TABLE calculations.tag_data
    RENAME COLUMN report_name_calc_id TO report_name_id;


