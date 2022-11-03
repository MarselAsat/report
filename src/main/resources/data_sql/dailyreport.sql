SET search_path TO recurring_reports;

-- Для суточного отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (1, 1, 'daily_mass_il1', 'Средний массовый расход', 'daily'),
       (2, 2, 'daily_temp_il1', 'Средняя температура', 'daily'),
       (3, 3, 'daily_pressure_il1', 'Среднее давление', 'daily'),
       (4, 4, 'daily_density_il1', 'Средняя плотность при текущих t и P', 'daily'),
       (5, 5, 'daily_density20_il1', 'Средняя плотность при 20 °C', 'daily'),
       (6, 6, 'daily_density15_il1', 'Средняя плотность при 15 °C', 'daily'),
       (7, 7, 'daily_moisture_il1', 'Среднее влагосодержание', 'daily'),
       (8, 8, 'daily_mass_gross_il1', 'Масса брутто за сутки', 'daily'),
       (9, 9, 'daily_mass_gross_increasing_il1', 'Масса брутто нарастающая', 'daily'),
       (10, 10, 'daily_vol_il1', 'Объем за сутки', 'daily'),
       (11, 11, 'daily_vol_increasing_il1', 'Объем нарастающий', 'daily');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (12, 1, 'daily_mass_il2', 'Средний массовый расход', 'daily'),
       (13, 2, 'daily_temp_il2', 'Средняя температура', 'daily'),
       (14, 3, 'daily_pressure_il2', 'Среднее давление', 'daily'),
       (15, 4, 'daily_density_il2', 'Средняя плотность при текущих t и P', 'daily'),
       (16, 5, 'daily_density20_il2', 'Средняя плотность при 20 °C', 'daily'),
       (17, 6, 'daily_density15_il2', 'Средняя плотность при 15 °C', 'daily'),
       (18, 7, 'daily_moisture_il2', 'Среднее влагосодержание', 'daily'),
       (19, 8, 'daily_mass_gross_il2', 'Масса брутто за сутки', 'daily'),
       (20, 9, 'daily_mass_gross_increasing_il2', 'Масса брутто нарастающая', 'daily'),
       (21, 10, 'daily_vol_il2', 'Объем за сутки', 'daily'),
       (22, 11, 'daily_vol_increasing_il2', 'Объем нарастающий', 'daily');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (23, 1, 'daily_mass_sikn', 'Средний массовый расход', 'daily'),
       (24, 2, 'daily_temp_sikn', 'Средняя температура', 'daily'),
       (25, 3, 'daily_pressure_sikn', 'Среднее давление', 'daily'),
       (26, 4, 'daily_density_sikn', 'Средняя плотность при текущих t и P', 'daily'),
       (27, 5, 'daily_density20_sikn', 'Средняя плотность при 20 °C', 'daily'),
       (28, 6, 'daily_density15_sikn', 'Средняя плотность при 15 °C', 'daily'),
       (29, 7, 'daily_moisture_sikn', 'Среднее влагосодержание', 'daily'),
       (30, 8, 'daily_mass_gross_sikn', 'Масса брутто за сутки', 'daily'),
       (31, 9, 'daily_mass_gross_increasing_sikn', 'Масса брутто нарастающая', 'daily'),
       (32, 10, 'daily_vol_sikn', 'Объем за сутки', 'daily'),
       (33, 11, 'daily_vol_increasing_sikn', 'Объем нарастающий', 'daily');

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (34, 1, 'daily_mass_bik', 'Средний массовый расход', 'daily'),
       (35, 2, 'daily_temp_bik', 'Средняя температура', 'daily'),
       (36, 3, 'daily_pressure_bik', 'Среднее давление', 'daily'),
       (37, 4, 'daily_density_bik', 'Средняя плотность при текущих t и P', 'daily'),
       (38, 5, 'daily_density20_bik', 'Средняя плотность при 20 °C', 'daily'),
       (39, 6, 'daily_density15_bik', 'Средняя плотность при 15 °C', 'daily'),
       (40, 7, 'daily_moisture_bik', 'Среднее влагосодержание', 'daily'),
       (41, 8, 'daily_mass_gross_bik', 'Масса брутто за сутки', 'daily'),
       (42, 9, 'daily_mass_gross_increasing_bik', 'Масса брутто нарастающая', 'daily'),
       (43, 10, 'daily_vol_bik', 'Объем за сутки', 'daily'),
       (44, 11, 'daily_vol_increasing_bik', 'Объем нарастающий', 'daily');
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (1, 'daily',
        'Суточный отчет за 20.04.2022',
        TO_TIMESTAMP('2022-04-20 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 23, 1),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 24, 1),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 25, 1),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 26, 1),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 27, 1),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 28, 1),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 29, 1),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 30, 1),
       (80353, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 31, 1),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 32, 1),
       (86360, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 33, 1);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 5, 1),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 6, 1),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 8, 1),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 9, 1),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 10, 1),
       (1978, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 11, 1);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 12, 1),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 13, 1),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 14, 1),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 15, 1),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 16, 1),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 17, 1),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 19, 1),
       (78566, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 20, 1),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 21, 1),
       (84382, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 22, 1);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 35, 1),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 36, 1),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 37, 1),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 38, 1),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 39, 1),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 40, 1);