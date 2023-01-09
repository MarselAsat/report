--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO public;

CREATE TABLE IF NOT EXISTS "user"
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(256),
    password VARCHAR(256),
    role     VARCHAR(256)
);

--changeset alina.parfenteva:2
CREATE TABLE IF NOT EXISTS settings
(
    id      SERIAL PRIMARY KEY,
    "group" VARCHAR(32),
    name    VARCHAR(256),
    value   VARCHAR(256)
);
