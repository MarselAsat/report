--liquibase formatted sql

--changeset alina.parfenteva:1
-- noinspection SqlResolve
ALTER SEQUENCE calculations.report_name_calc_id_seq
    RENAME TO report_name_id_seq;

-- noinspection SqlResolve
ALTER SEQUENCE calculations.manual_tag_name_id_seq
    RENAME TO tag_name_id_seq;

-- noinspection SqlResolve
ALTER SEQUENCE calculations.tag_data_seq
    RENAME TO tag_data_id_seq;


