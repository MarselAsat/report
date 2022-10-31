--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER SEQUENCE poverka.report_name_poverka_id_seq
    RENAME TO report_name_id_seq;

ALTER SEQUENCE poverka.manual_tag_name_id_seq
    RENAME TO tag_name_id_seq;

ALTER SEQUENCE poverka.tag_data_seq
    RENAME TO tag_data_id_seq;


