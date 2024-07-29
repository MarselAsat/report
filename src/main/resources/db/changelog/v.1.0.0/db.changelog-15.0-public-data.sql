--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO "user" (username, password, role)
VALUES ('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN');

--changeset alina.parfenteva:2
INSERT INTO settings ("group", name, value)
VALUES   ('report view', 'minute report columns', 'sikn,il1,il2,il3,il4,bik'),
