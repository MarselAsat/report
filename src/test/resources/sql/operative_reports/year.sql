SET search_path TO operative_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты формируемые за год', TRUE),
       ('manual', 'Ручной', 'Для поверок', TRUE);

--Для годового отчета
INSERT INTO tag (id, "order", address, description, report_type_id)
VALUES (127, 1, 'year_mass_sikn', 'Средний массовый расход', 'year'),
       (128, 2, 'year_temp_sikn', 'Средняя температура', 'year'),
       (129, 3, 'year_pressure_sikn', 'Среднее давление', 'year');

INSERT INTO tag (id, "order", address, description, report_type_id)
VALUES (130, 1, 'year_mass_il1', 'Средний массовый расход', 'year'),
       (131, 2, 'year_temp_il1', 'Средняя температура', 'year'),
       (132, 3, 'year_pressure_il1', 'Среднее давление', 'year'),
       (133, 4, 'year_density_il1', 'Средняя плотность при текущих t и P', 'year');

INSERT INTO tag (id, "order", address, description, report_type_id)
VALUES (134, 1, 'year_mass_il2', 'Средний массовый расход', 'year'),
       (135, 2, 'year_temp_il2', 'Средняя температура', 'year'),
       (136, 3, 'year_pressure_il2', 'Среднее давление', 'year'),
       (137, 4, 'year_density_il2', 'Средняя плотность при текущих t и P', 'year');


INSERT INTO tag (id, "order", address, description, report_type_id)
VALUES (138, 1, 'year_mass_bik', 'Средний массовый расход', 'year'),
       (139, 2, 'year_temp_bik', 'Средняя температура', 'year'),
       (140, 3, 'year_pressure_bik', 'Среднее давление', 'year');

INSERT INTO tag (id, "order", address, description, report_type_id)
VALUES (141, 1, 'year_mass_il4', 'Средний массовый расход', 'year'),
       (142, 2, 'year_temp_il4', 'Средняя температура', 'year'),
       (143, 3, 'year_pressure_il4', 'Среднее давление', 'year');
SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(id, report_type_id, address, start_dt, end_dt, creation_dt)
VALUES (5, 'year',
        'Годовой отчет за 2021',
        TO_TIMESTAMP('2021-01-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-01-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-01-01 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));
SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report));

-- СИКН
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 127, 5),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 128, 5),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 129, 5);

-- ИЛ1
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 130, 5),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 131, 5),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 132, 5),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 133, 5);

-- ИЛ2
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 134, 5),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 135, 5),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 136, 5),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 137, 5);

-- БИК
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 138, 5),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 139, 5),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 140, 5);

-- ИЛ4
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 141, 5),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 142, 5),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 143, 5);