--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO public;

CREATE TABLE IF NOT EXISTS "user"
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    role     VARCHAR(256) NOT NULL
);

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS settings
(
    id      SERIAL PRIMARY KEY,
    "group" VARCHAR(32) NOT NULL,
    name    VARCHAR(256) NOT NULL,
    value   VARCHAR(256) NOT NULL
);
