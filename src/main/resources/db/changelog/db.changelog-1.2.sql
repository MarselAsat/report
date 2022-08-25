--liquibase formatted sql

--changeset alina.parfenteva:1
ALTER SEQUENCE user_id_seq AS bigint;
ALTER TABLE "user" ALTER id TYPE bigint;
