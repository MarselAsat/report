--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE SCHEMA IF NOT EXISTS poverka;
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
ALTER TABLE report_name_poverka
    SET SCHEMA poverka;
ALTER TABLE manual_tag_name
    SET SCHEMA poverka;
ALTER TABLE tag_data_poverka3622
    SET SCHEMA poverka;

--changeset alina.parfenteva:4
DROP TABLE text_tag_data;

--changeset alina.parfenteva:5
ALTER TABLE poverka.report_name_poverka
    RENAME TO report_name;
ALTER TABLE poverka.manual_tag_name
    RENAME TO tag_name;
ALTER TABLE poverka.tag_data_poverka3622
    RENAME TO tag_data;

--changeset alina.parfenteva:6
ALTER SEQUENCE tag_data_poverka3622_seq
    SET SCHEMA poverka;

--changeset alina.parfenteva:7
ALTER SEQUENCE poverka.tag_data_poverka3622_seq
    RENAME TO tag_data_seq;


