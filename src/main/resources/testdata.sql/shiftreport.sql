SET search_path TO scheduled_reports;

INSERT INTO report_row (name, "order", report_type_id)
VALUES ('Средний массовый расход', 1, 'shift'),
       ('Средняя температура', 2, 'shift'),
       ('Среднее давление', 3, 'shift');

SELECT SETVAL('report_row_id_seq', (SELECT MAX(id) FROM report_row));

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES ((SELECT id FROM report_row rr WHERE name = 'Средний массовый расход' AND rr.report_type_id = 'shift'),
        'shift_mass_il1', 'Средний массовый расход за смену ил1', 'shift', 'il1'),
       ((SELECT id FROM report_row rr WHERE name = 'Средняя температура' AND rr.report_type_id = 'shift'),
        'shift_temp_il1', 'Средняя температура за смену ил1', 'shift', 'il1'),
       ((SELECT id FROM report_row rr WHERE name = 'Среднее давление' AND rr.report_type_id = 'shift'),
        'shift_pressure_il1', 'Среднее давление за смену ил1', 'shift', 'il1');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES ((SELECT id FROM report_row WHERE name = 'Средний массовый расход' AND report_row.report_type_id = 'shift'),
        'shift_mass_il2', 'Средний массовый расход за смену ил2', 'shift', 'il2'),
       ((SELECT id FROM report_row WHERE name = 'Средняя температура' AND report_row.report_type_id = 'shift'),
        'shift_temp_il2', 'Средняя температура за смену ил2', 'shift', 'il2'),
       ((SELECT id FROM report_row WHERE name = 'Среднее давление' AND report_row.report_type_id = 'shift'),
        'shift_pressure_il2', 'Среднее давление за смену ил2', 'shift', 'il2');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES ((SELECT id FROM report_row WHERE name = 'Средний массовый расход' AND report_type_id = 'shift'),
        'shift_mass_sikn', 'Средний массовый расход за смену сикн', 'shift', 'sikn'),
       ((SELECT id FROM report_row WHERE name = 'Средняя температура' AND report_type_id = 'shift'),
        'shift_temp_sikn', 'Средняя температура за смену сикн', 'shift', 'sikn'),
       ((SELECT id FROM report_row WHERE name = 'Среднее давление' AND report_type_id = 'shift'),
        'shift_pressure_sikn', 'Среднее давление за смену сикн', 'shift', 'sikn');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES ((SELECT id FROM report_row WHERE name = 'Средний массовый расход' AND report_type_id = 'shift'),
        'shift_mass_bik', 'Средний массовый расход', 'shift', 'bik'),
       ((SELECT id FROM report_row WHERE name = 'Средняя температура' AND report_type_id = 'shift'),
        'shift_temp_bik', 'Средняя температура', 'shift', 'bik'),
       ((SELECT id FROM report_row WHERE name = 'Среднее давление' AND report_type_id = 'shift'),
        'shift_pressure_bik', 'Среднее давление', 'shift', 'bik');

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(report_type_id, name, start_dt, end_dt, creation_dt)
VALUES ('shift',
        'Тестовый сменный отчет за 1 смену 21.04.2022',
        TO_TIMESTAMP('2022-04-21 09:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 18:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 18:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_mass_il1'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_temp_il1'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_pressure_il1'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_mass_il2'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_temp_il2'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_pressure_il2'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_mass_sikn'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_temp_sikn'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_pressure_sikn'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_mass_bik'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_temp_bik'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022')),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM tag WHERE address = 'shift_pressure_bik'),
        (SELECT id FROM report WHERE name = 'Тестовый сменный отчет за 1 смену 21.04.2022'));

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));
SELECT SETVAL('report_id_seq', (SELECT MAX(id) FROM report));