--liquibase formatted sql

--changeset alina.parfenteva:1
UPDATE settings
SET name = 'daily report start time'
WHERE name = 'start daily report';

UPDATE settings
SET name = 'shift report start time'
WHERE name = 'start shift report';

UPDATE settings
SET name = 'month report start time'
WHERE name = 'start month report';

UPDATE settings
SET name = 'year report start time'
WHERE name = 'start year report';

