--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER TABLE tag_name
ADD COLUMN "order" INT;
