SET search_path TO operative_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты формируемые за год', TRUE);

-- Для часового отчета
INSERT INTO tag (id, address, description, report_type_id)
VALUES (45, 'hour_mass_il1', 'Средний массовый расход', 'hour'),
       (46, 'hour_temp_il1', 'Средняя температура', 'hour'),
       (47, 'hour_pressure_il1', 'Среднее давление', 'hour'),
       (48, 'hour_density_il1', 'Средняя плотность при текущих t и P', 'hour'),
       (49, 'hour_density20_il1', 'Средняя плотность при 20 °C', 'hour'),
       (50, 'hour_density15_il1', 'Средняя плотность при 15 °C', 'hour'),
       (51, 'hour_moisture_il1', 'Среднее влагосодержание', 'hour'),
       (52, 'hour_mass_gross_il1', 'Масса брутто за сутки', 'hour'),
       (53, 'hour_mass_gross_increasing_il1', 'Масса брутто нарастающая', 'hour'),
       (54, 'hour_vol_il1', 'Объем за сутки', 'hour'),
       (55, 'hour_vol_increasing_il1', 'Объем нарастающий', 'hour');

INSERT INTO tag (id, address, description, report_type_id)
VALUES (56, 'hour_mass_il2', 'Средний массовый расход', 'hour'),
       (57, 'hour_temp_il2', 'Средняя температура', 'hour'),
       (58, 'hour_pressure_il2', 'Среднее давление', 'hour'),
       (59, 'hour_density_il2', 'Средняя плотность при текущих t и P', 'hour'),
       (60, 'hour_density20_il2', 'Средняя плотность при 20 °C', 'hour'),
       (61, 'hour_density15_il2', 'Средняя плотность при 15 °C', 'hour'),
       (62, 'hour_moisture_il2', 'Среднее влагосодержание', 'hour'),
       (63, 'hour_mass_gross_il2', 'Масса брутто за сутки', 'hour'),
       (64, 'hour_mass_gross_increasing_il2', 'Масса брутто нарастающая', 'hour'),
       (65, 'hour_vol_il2', 'Объем за сутки', 'hour'),
       (66, 'hour_vol_increasing_il2', 'Объем нарастающий', 'hour'),
       (67, 'hour_vol_decreasing_il2', 'Объем убывающий', 'hour');

INSERT INTO tag (id, address, description, report_type_id)
VALUES (68, 'hour_mass_sikn', 'Средний массовый расход', 'hour'),
       (69, 'hour_temp_sikn', 'Средняя температура', 'hour'),
       (70, 'hour_pressure_sikn', 'Среднее давление', 'hour'),
       (71, 'hour_density_sikn', 'Средняя плотность при текущих t и P', 'hour'),
       (72, 'hour_density20_sikn', 'Средняя плотность при 20 °C', 'hour'),
       (73, 'hour_density15_sikn', 'Средняя плотность при 15 °C', 'hour'),
       (74, 'hour_moisture_sikn', 'Среднее влагосодержание', 'hour'),
       (75, 'hour_mass_gross_sikn', 'Масса брутто за сутки', 'hour'),
       (76, 'hour_mass_gross_increasing_sikn', 'Масса брутто нарастающая', 'hour'),
       (77, 'hour_vol_sikn', 'Объем за сутки', 'hour'),
       (78, 'hour_vol_increasing_sikn', 'Объем нарастающий', 'hour');

INSERT INTO tag (id, address, description, report_type_id)
VALUES (79, 'hour_mass_bik', 'Средний массовый расход', 'hour'),
       (80, 'hour_temp_bik', 'Средняя температура', 'hour'),
       (81, 'hour_pressure_bik', 'Среднее давление', 'hour'),
       (82, 'hour_density_bik', 'Средняя плотность при текущих t и P', 'hour'),
       (83, 'hour_density20_bik', 'Средняя плотность при 20 °C', 'hour'),
       (84, 'hour_density15_bik', 'Средняя плотность при 15 °C', 'hour'),
       (85, 'hour_moisture_bik', 'Среднее влагосодержание', 'hour'),
       (86, 'hour_mass_gross_bik', 'Масса брутто за сутки', 'hour'),
       (87, 'hour_mass_gross_increasing_bik', 'Масса брутто нарастающая', 'hour'),
       (88, 'hour_vol_bik', 'Объем за сутки', 'hour'),
       (89, 'hour_vol_increasing_bik', 'Объем нарастающий', 'hour');

INSERT INTO tag (id, address, description, report_type_id)
VALUES (90, 'hour_mass_il4', 'Средний массовый расход', 'hour'),
       (91, 'hour_temp_il4', 'Средняя температура', 'hour'),
       (92, 'hour_pressure_il4', 'Среднее давление', 'hour');
SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (2, 'hour',
        'Часовой отчет за 12 часов 20.04.2022',
        TO_TIMESTAMP('2022-04-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 13:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 13:01:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 68, 2),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 69, 2),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 70, 2),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 71, 2),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 72, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 73, 2),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 74, 2),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 75, 2),
       (80353, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 76, 2),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 77, 2),
       (86360, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 78, 2);

-- ИЛ1
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 45, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 50, 2),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 51, 2),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 52, 2),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 53, 2),
       (1978, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 55, 2);

-- ИЛ2
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 60, 2),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 61, 2),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 62, 2),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 63, 2),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 64, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 65, 2),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 66, 2),
       (78566, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 67, 2),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 59, 2),
       (84382, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 58, 2);

-- БИК
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 80, 2),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 81, 2),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 82, 2),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 83, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 84, 2),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 85, 2);

-- ИЛ4
INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 90, 2),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 91, 2),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 92, 2);
