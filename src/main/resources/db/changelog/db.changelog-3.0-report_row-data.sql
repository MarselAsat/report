--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO scheduled_reports;

insert into report_row (id, name, "order", report_type_id)
values (12, 'Средний массовый расход', 11,'hour'),
       (13, 'Средняя температура', 10,'hour'),
       (14, 'Среднее давление', 9,'hour'),
       (15, 'Средняя плотность при текущих t и P', 8,'hour'),
       (16, 'Средняя плотность при 20 °C', 7,'hour'),
       (17, 'Средняя плотность при 15 °C', 6,'hour'),
       (18, 'Среднее влагосодержание', 5,'hour'),
       (19, 'Масса брутто за час', 4,'hour'),
       (20, 'Масса брутто нарастающая', 3,'hour'),
       (21, 'Объем за час', 2,'hour'),
       (22, 'Объем нарастающий', 1,'hour');

SELECT SETVAL('report_row_id_seq', (SELECT MAX(id) FROM report_row));