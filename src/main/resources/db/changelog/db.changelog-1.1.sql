--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER TABLE tag_data
ALTER COLUMN data SET DATA TYPE float8;
