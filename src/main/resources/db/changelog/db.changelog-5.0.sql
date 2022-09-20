--liquibase formatted sql

--changeset alina.parfenteva:1
UPDATE settings
SET "group" = 'start time of report'
WHERE "group" = 'start date of report';

--changeset alina.parfenteva:2
INSERT INTO settings ("group", name, value)
VALUES ('start time of report', 'start daily report', '10:00'),
       ('start time of report', 'start month report', '10:00'),
       ('start time of report', 'start year report', '10:00');