SET search_path TO recurring_reports;

insert into recurring_reports.report_row (id, name, "order", report_type_id)
values (23, 'Средний массовый расход', 11,'shift'),
       (24, 'Средняя температура', 10,'shift'),
       (25, 'Среднее давление', 9,'shift');

SELECT SETVAL('report_row_id_seq', (SELECT MAX(id) FROM report_row));

INSERT INTO tag_name (id, row_id, name, description, report_type_id, metering_node_id)
VALUES (89, 23, 'shift_mass_il1', 'Средний массовый расход за смену ил1', 'shift', 'il1'),
       (90, 24, 'shift_temp_il1', 'Средняя температура за смену ил1', 'shift', 'il1'),
       (91, 25, 'shift_pressure_il1', 'Среднее давление за смену ил1', 'shift', 'il1');

INSERT INTO tag_name (id, row_id, name, description, report_type_id, metering_node_id)
VALUES (92, 23, 'shift_mass_il2', 'Средний массовый расход за смену ил2', 'shift', 'il2'),
       (93, 24, 'shift_temp_il2', 'Средняя температура за смену ил2', 'shift', 'il2'),
       (94, 25, 'shift_pressure_il2', 'Среднее давление за смену ил2', 'shift', 'il2');

INSERT INTO tag_name (id, row_id, name, description, report_type_id, metering_node_id)
VALUES (95, 23, 'shift_mass_sikn', 'Средний массовый расход за смену сикн', 'shift', 'sikn'),
       (96, 24, 'shift_temp_sikn', 'Средняя температура за смену сикн', 'shift', 'sikn'),
       (97, 25, 'shift_pressure_sikn', 'Среднее давление за смену сикн', 'shift', 'sikn');

INSERT INTO tag_name (id, row_id, name, description, report_type_id, metering_node_id)
VALUES (98, 23, 'shift_mass_bik', 'Средний массовый расход', 'shift', 'bik'),
       (99, 24, 'shift_temp_bik', 'Средняя температура', 'shift', 'bik'),
       (100, 25, 'shift_pressure_bik', 'Среднее давление', 'shift', 'bik');

SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (3, 'shift',
        'Тестовый сменный отчет за 10:00 21.04.2022',
        TO_TIMESTAMP('2022-04-21 09:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 89, 3),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 90, 3),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 91, 3);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 92, 3),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 93, 3),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 94, 3);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 95, 3),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 96, 3),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 97, 3);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 98, 3),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 99, 3),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 3);

SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));
SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report_name));