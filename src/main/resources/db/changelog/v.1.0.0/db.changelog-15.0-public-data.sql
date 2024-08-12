--liquibase formatted sql
--liquibase formatted sql

--changeset yourname:1
ALTER TABLE public.settings ADD COLUMN time_check TIMESTAMP;
--changeset yourname:1
ALTER TABLE public.settings
ALTER COLUMN "check" TYPE varchar(255);
ALTER TABLE public.settings
ALTER COLUMN "start_time" TYPE varchar(255);

INSERT INTO settings ("group", name, value, "check", "start_time")
VALUES
    ('report view', 'hour report columns', 'sikn,il1,il2,il3,il4,bik', 'no', 'no'),
    ('report view', 'twohour report columns', 'sikn,il1,il2,il3,il4,bik', 'no', 'no'),
    ('report view', 'daily report columns', 'sikn,il1,il2,il3,il4,bik', 'start time report', '10:00'),
    ('report view', 'shift report columns', 'sikn,il1,il2,il3,il4,bik', 'TRUE', '1-10:00;2-22:00'),
    ('report view', 'month report columns', 'sikn,il1,il2,il3,il4,bik', 'TRUE', '10:00'),
    ('report view', 'year report columns', 'sikn,il1,il2,il3,il4,bik', 'TRUE', '10:00'),
    ('report view', 'minute report columns', 'sikn,il1,il2,il3,il4,bik', 'no', 'no'),
    ('report view', 'metering station name', 'СИКН №1524', 'no', 'no');