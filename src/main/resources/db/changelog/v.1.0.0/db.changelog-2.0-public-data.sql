--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO "user" (username, password, role)
VALUES ('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN');

--changeset alina.parfenteva:2
INSERT INTO settings ("group", name, value)
VALUES ('report view', 'hour report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'twohour report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'daily report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'shift report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'month report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'year report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'metering station name', 'СИКН №1524'),
       ('start time of report', 'shift report start time', '1-10:00,2-22:00'),
       ('start time of report', 'daily report start time', '10:00'),
       ('start time of report', 'month report start time', '10:00'),
       ('start time of report', 'year report start time', '10:00');