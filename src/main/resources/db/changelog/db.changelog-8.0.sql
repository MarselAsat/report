-- noinspection SqlResolveForFile

--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS calculations;
CREATE SCHEMA IF NOT EXISTS recurring_reports;

--changeset alina.parfenteva:2
ALTER TABLE report_name
    SET SCHEMA recurring_reports;
ALTER TABLE report_type
    SET SCHEMA recurring_reports;
ALTER TABLE tag_data
    SET SCHEMA recurring_reports;
ALTER TABLE tag_name
    SET SCHEMA recurring_reports;

--changeset alina.parfenteva:3
ALTER TABLE report_name_calc
    SET SCHEMA calculations;
ALTER TABLE manual_tag_name
    SET SCHEMA calculations;
ALTER TABLE tag_data_calc3622
    SET SCHEMA calculations;

--changeset alina.parfenteva:4
DROP TABLE text_tag_data;

--changeset alina.parfenteva:5
ALTER TABLE calculations.report_name_calc
    RENAME TO report_name;
ALTER TABLE calculations.manual_tag_name
    RENAME TO tag_name;
ALTER TABLE calculations.tag_data_calc3622
    RENAME TO tag_data;

--changeset alina.parfenteva:6
ALTER SEQUENCE tag_data_calc3622_seq
    SET SCHEMA calculations;

--changeset alina.parfenteva:7
ALTER SEQUENCE calculations.tag_data_calc3622_seq
    RENAME TO tag_data_seq;


