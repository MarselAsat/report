--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER TABLE text_tag_data ALTER manual_tag_name_id TYPE INT;
