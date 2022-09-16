--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE TABLE IF NOT EXISTS settings
(
    id    SERIAL PRIMARY KEY,
    "group" VARCHAR(32),
    name  VARCHAR(256),
    value VARCHAR(256)
);

INSERT INTO settings ("group", name,value)
VALUES ('report view', 'Часовой отчет: столбцы', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'Суточный отчет: столбцы', 'sikn,il1,il2,bik'),
       ('report view', 'Сменный отчет: столбцы', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'Месячный отчет: столбцы', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'Годовой отчет: столбцы', 'sikn,il1,il2,il3,il4,bik');