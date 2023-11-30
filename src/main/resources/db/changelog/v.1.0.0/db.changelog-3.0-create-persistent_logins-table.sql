--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO public;

CREATE TABLE IF NOT EXISTS persistent_logins
(
    series    VARCHAR(64) PRIMARY KEY,
    username  VARCHAR(64) NOT NULL,
    token     VARCHAR(64) NOT NULL,
    last_used TIMESTAMP   NOT NULL
);