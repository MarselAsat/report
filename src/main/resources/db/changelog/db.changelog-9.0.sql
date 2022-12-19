--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO settings ("group", name, value)
VALUES ('MI3622Calculation view', 'MI3622 6 or 7 table', '6');


