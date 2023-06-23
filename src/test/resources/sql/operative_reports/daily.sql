SET search_path TO operative_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE);

INSERT INTO report_row (id, name, "order", report_type_id)
VALUES (1, 'Средний массовый расход', 1, 'daily'),
       (2, 'Средняя температура', 2, 'daily'),
       (3, 'Среднее давление', 3, 'daily'),
       (4, 'Средняя плотность при текущих t и P', 4, 'daily'),
       (5, 'Средняя плотность при 20 °C', 5, 'daily'),
       (6, 'Средняя плотность при 15 °C', 6, 'daily'),
       (7, 'Среднее влагосодержание', 7, 'daily'),
       (8, 'Масса брутто за сутки', 8, 'daily'),
       (9, 'Масса брутто нарастающая', 9, 'daily'),
       (10, 'Объем за сутки', 10, 'daily'),
       (11, 'Объем нарастающий', 11, 'daily');

INSERT INTO metering_node(id, name)
VALUES ('il1', 'ИЛ №1'),
       ('il2', 'ИЛ №2'),
       ('sikn', 'СИКН'),
       ('bik', 'БИК');

-- Для суточного отчета
INSERT INTO tag (id, row_id, address, description, report_type_id, metering_node_id)
VALUES (1, 1, 'daily_mass_il1', 'Средний массовый расход', 'daily', 'il1'),
       (2, 2, 'daily_temp_il1', 'Средняя температура', 'daily', 'il1'),
       (3, 3, 'daily_pressure_il1', 'Среднее давление', 'daily', 'il1'),
       (4, 4, 'daily_density_il1', 'Средняя плотность при текущих t и P', 'daily', 'il1'),
       (5, 5, 'daily_density20_il1', 'Средняя плотность при 20 °C', 'daily', 'il1'),
       (6, 6, 'daily_density15_il1', 'Средняя плотность при 15 °C', 'daily', 'il1'),
       (7, 7, 'daily_moisture_il1', 'Среднее влагосодержание', 'daily', 'il1'),
       (8, 8, 'daily_mass_gross_il1', 'Масса брутто за сутки', 'daily', 'il1'),
       (9, 9, 'daily_mass_gross_increasing_il1', 'Масса брутто нарастающая', 'daily', 'il1'),
       (10, 10, 'daily_vol_il1', 'Объем за сутки', 'daily', 'il1'),
       (11, 11, 'daily_vol_increasing_il1', 'Объем нарастающий', 'daily', 'il1');

INSERT INTO tag (id, row_id, address, description, report_type_id, metering_node_id)
VALUES (12, 1, 'daily_mass_il2', 'Средний массовый расход', 'daily', 'il2'),
       (13, 2, 'daily_temp_il2', 'Средняя температура', 'daily', 'il2'),
       (14, 3, 'daily_pressure_il2', 'Среднее давление', 'daily', 'il2'),
       (15, 4, 'daily_density_il2', 'Средняя плотность при текущих t и P', 'daily', 'il2'),
       (16, 5, 'daily_density20_il2', 'Средняя плотность при 20 °C', 'daily', 'il2'),
       (17, 6, 'daily_density15_il2', 'Средняя плотность при 15 °C', 'daily', 'il2'),
       (18, 7, 'daily_moisture_il2', 'Среднее влагосодержание', 'daily', 'il2'),
       (19, 8, 'daily_mass_gross_il2', 'Масса брутто за сутки', 'daily', 'il2'),
       (20, 9, 'daily_mass_gross_increasing_il2', 'Масса брутто нарастающая', 'daily', 'il2'),
       (21, 10, 'daily_vol_il2', 'Объем за сутки', 'daily', 'il2'),
       (22, 11, 'daily_vol_increasing_il2', 'Объем нарастающий', 'daily', 'il2');

INSERT INTO tag (id, row_id, address, description, report_type_id, metering_node_id)
VALUES (23, 1, 'daily_mass_sikn', 'Средний массовый расход', 'daily', 'sikn'),
       (24, 2, 'daily_temp_sikn', 'Средняя температура', 'daily', 'sikn'),
       (25, 3, 'daily_pressure_sikn', 'Среднее давление', 'daily', 'sikn'),
       (26, 4, 'daily_density_sikn', 'Средняя плотность при текущих t и P', 'daily', 'sikn'),
       (27, 5, 'daily_density20_sikn', 'Средняя плотность при 20 °C', 'daily', 'sikn'),
       (28, 6, 'daily_density15_sikn', 'Средняя плотность при 15 °C', 'daily', 'sikn'),
       (29, 7, 'daily_moisture_sikn', 'Среднее влагосодержание', 'daily', 'sikn'),
       (30, 8, 'daily_mass_gross_sikn', 'Масса брутто за сутки', 'daily', 'sikn'),
       (31, 9, 'daily_mass_gross_increasing_sikn', 'Масса брутто нарастающая', 'daily', 'sikn'),
       (32, 10, 'daily_vol_sikn', 'Объем за сутки', 'daily', 'sikn'),
       (33, 11, 'daily_vol_increasing_sikn', 'Объем нарастающий', 'daily', 'sikn');

INSERT INTO tag (id, row_id, address, description, report_type_id, metering_node_id)
VALUES (34, 1, 'daily_mass_bik', 'Средний массовый расход', 'daily', 'bik'),
       (35, 2, 'daily_temp_bik', 'Средняя температура', 'daily', 'bik'),
       (36, 3, 'daily_pressure_bik', 'Среднее давление', 'daily', 'bik'),
       (37, 4, 'daily_density_bik', 'Средняя плотность при текущих t и P', 'daily', 'bik'),
       (38, 5, 'daily_density20_bik', 'Средняя плотность при 20 °C', 'daily', 'bik'),
       (39, 6, 'daily_density15_bik', 'Средняя плотность при 15 °C', 'daily', 'bik'),
       (40, 7, 'daily_moisture_bik', 'Среднее влагосодержание', 'daily', 'bik'),
       (41, 8, 'daily_mass_gross_bik', 'Масса брутто за сутки', 'daily', 'bik'),
       (42, 9, 'daily_mass_gross_increasing_bik', 'Масса брутто нарастающая', 'daily', 'bik'),
       (43, 10, 'daily_vol_bik', 'Объем за сутки', 'daily', 'bik'),
       (44, 11, 'daily_vol_increasing_bik', 'Объем нарастающий', 'daily', 'bik');
SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (1, 'daily',
        'Суточный отчет за 20.04.2022',
        TO_TIMESTAMP('2022-04-20 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
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

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 5, 1),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 6, 1),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 8, 1),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 9, 1),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 10, 1),
       (1978, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 11, 1);

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
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

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 35, 1),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 36, 1),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 37, 1),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 38, 1),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 39, 1),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 40, 1);