--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO public;

-- Удаление всех записей из таблицы settings
DELETE FROM settings;

-- Изменение типов данных столбцов в таблице settings
ALTER TABLE public.settings
    ADD COLUMN IF NOT EXISTS "check" varchar(255);

ALTER TABLE public.settings
    ADD COLUMN IF NOT EXISTS "start_time" varchar(255);

ALTER TABLE public.settings
    ADD COLUMN IF NOT EXISTS "time_check" INTEGER;

UPDATE settings
SET "group" = NULL,
    name = NULL,
    value = NULL,
    "check" = NULL,
    start_time = NULL,
    time_check = NULL;

-- Вставка новых записей в таблицу settings
INSERT INTO settings ("group", name, value, "check", "start_time", "time_check")
VALUES
    ('report view', 'hour report columns', 'sikn,il1,il2,il3,il4,bik', 'no', 'no', NULL),
    ('report view', 'twohour report columns', 'sikn,il1,il2,il3,il4,bik', 'no', 'no', NULL),
    ('report view', 'daily report columns', 'sikn,il1,il2,il3,il4,bik', 'start time report', '10:00', NULL),
    ('report view', 'shift report columns', 'sikn,il1,il2,il3,il4,bik', 'TRUE', '1-10:00,2-22:00', NULL),
    ('report view', 'month report columns', 'sikn,il1,il2,il3,il4,bik', 'TRUE', '10:00', NULL),
    ('report view', 'year report columns', 'sikn,il1,il2,il3,il4,bik', 'TRUE', '10:00', NULL),
    ('report view', 'minute report columns', 'sikn,il1,il2,il3,il4,bik', 'no', 'no', NULL),
    ('report view', 'metering station name', 'СИКН №1524', 'no', 'no', NULL);