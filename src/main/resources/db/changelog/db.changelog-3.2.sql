--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO settings ("group", name,value)
VALUES ('report view', 'metering station name', 'СИКН №1524');