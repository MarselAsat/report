SET search_path TO recurring_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты формируемые за год', TRUE);

--Для сменного отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (93, 1, 'shift_mass_il1', 'Средний массовый расход', 'shift'),
       (94, 2, 'shift_temp_il1', 'Средняя температура', 'shift'),
       (95, 3, 'shift_pressure_il1', 'Среднее давление', 'shift'),
       (96, 4, 'shift_density_il1', 'Средняя плотность при текущих t и P', 'shift');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (97, 1, 'shift_mass_il2', 'Средний массовый расход', 'shift'),
       (98, 2, 'shift_temp_il2', 'Средняя температура', 'shift'),
       (99, 3, 'shift_pressure_il2', 'Среднее давление', 'shift'),
       (100, 4, 'shift_density_il2', 'Средняя плотность при текущих t и P', 'shift');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (101, 1, 'shift_mass_sikn', 'Средний массовый расход', 'shift'),
       (102, 2, 'shift_temp_sikn', 'Средняя температура', 'shift'),
       (103, 3, 'shift_pressure_sikn', 'Среднее давление', 'shift');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (104, 1, 'shift_mass_bik', 'Средний массовый расход', 'shift'),
       (105, 2, 'shift_temp_bik', 'Средняя температура', 'shift'),
       (106, 3, 'shift_pressure_bik', 'Среднее давление', 'shift');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (107, 1, 'shift_mass_il4', 'Средний массовый расход', 'shift'),
       (108, 2, 'shift_temp_il4', 'Средняя температура', 'shift'),
       (109, 3, 'shift_pressure_il4', 'Среднее давление', 'shift');
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (3, 'shift',
        'Сменный отчет за 1 смену 20.04.2022',
        TO_TIMESTAMP('2022-04-20 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 22:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 22:00:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 101, 3),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 102, 3),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 103, 3),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 71, 3);

-- ИЛ1
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 93, 3),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 94, 3),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 95, 3),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 96, 3);

-- ИЛ2
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 97, 3),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 98, 3),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 99, 3),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 3);

-- БИК
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 104, 3),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 105, 3),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 106, 3);

-- ИЛ4
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 107, 3),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 108, 3),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 109, 3);

--Для месячного отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (110, 1, 'month_mass_il1', 'Средний массовый расход', 'month'),
       (111, 2, 'month_temp_il1', 'Средняя температура', 'month'),
       (112, 3, 'month_pressure_il1', 'Среднее давление', 'month'),
       (113, 4, 'month_density_il1', 'Средняя плотность при текущих t и P', 'month');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (114, 1, 'month_mass_il2', 'Средний массовый расход', 'month'),
       (115, 2, 'month_temp_il2', 'Средняя температура', 'month'),
       (116, 3, 'month_pressure_il2', 'Среднее давление', 'month'),
       (117, 4, 'month_density_il2', 'Средняя плотность при текущих t и P', 'month');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (118, 1, 'month_mass_sikn', 'Средний массовый расход', 'month'),
       (119, 2, 'month_temp_sikn', 'Средняя температура', 'month'),
       (120, 3, 'month_pressure_sikn', 'Среднее давление', 'month');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (121, 1, 'month_mass_bik', 'Средний массовый расход', 'month'),
       (122, 2, 'month_temp_bik', 'Средняя температура', 'month'),
       (123, 3, 'month_pressure_bik', 'Среднее давление', 'month');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (124, 1, 'month_mass_il4', 'Средний массовый расход', 'month'),
       (125, 2, 'month_temp_il4', 'Средняя температура', 'month'),
       (126, 3, 'month_pressure_il4', 'Среднее давление', 'month');
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (4, 'month',
        'Месячный отчет за 04.2022',
        TO_TIMESTAMP('2022-04-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-05-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-05-01 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 118, 4),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 119, 4),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 120, 4);

-- ИЛ1
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 110, 4),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 111, 4),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 112, 4),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 113, 4);

-- ИЛ2
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 114, 4),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 115, 4),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 116, 4),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 117, 4);

-- БИК
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 121, 4),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 122, 4),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 123, 4);

-- ИЛ4
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 124, 4),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 125, 4),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 126, 4);