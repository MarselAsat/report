--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO settings ("group", name, value)
VALUES ('start date of report', 'start shift report', '1-10:00,2-22:00');