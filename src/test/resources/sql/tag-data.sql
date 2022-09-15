INSERT INTO "user" (username, password, role)
VALUES ('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN'),
       ('user', '$2a$12$C9C9MlJl/AOOp4UVYkujn.Lxld46KI4SOGXWG34tb0jBE52nKXMGa', 'ROLE_USER');

INSERT INTO report_type (id, name, description, active)
VALUES (1, 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       (2, 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       (3, 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       (4, 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       (5, 'Годовой', 'Отчеты формируемые за год', TRUE),
       (6, 'Ручной', 'Для поверок', TRUE);

-- Для суточного отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (1, 1, 'daily_mass_il1', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (2, 2, 'daily_temp_il1', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (3, 3, 'daily_pressure_il1', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (4, 4, 'daily_density_il1', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (5, 5, 'daily_density20_il1', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (6, 6, 'daily_density15_il1', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (7, 7, 'daily_moisture_il1', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (8, 8, 'daily_mass_gross_il1', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (9, 9, 'daily_mass_gross_increasing_il1', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (10, 10, 'daily_vol_il1', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (11, 11, 'daily_vol_increasing_il1', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Суточный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (12, 1, 'daily_mass_il2', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (13, 2, 'daily_temp_il2', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (14, 3, 'daily_pressure_il2', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (15, 4, 'daily_density_il2', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (16, 5, 'daily_density20_il2', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (17, 6, 'daily_density15_il2', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (18, 7, 'daily_moisture_il2', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (19, 8, 'daily_mass_gross_il2', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (20, 9, 'daily_mass_gross_increasing_il2', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (21, 10, 'daily_vol_il2', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (22, 11, 'daily_vol_increasing_il2', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Суточный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (23, 1, 'daily_mass_sikn', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (24, 2, 'daily_temp_sikn', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (25, 3, 'daily_pressure_sikn', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (26, 4, 'daily_density_sikn', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (27, 5, 'daily_density20_sikn', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (28, 6, 'daily_density15_sikn', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (29, 7, 'daily_moisture_sikn', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (30, 8, 'daily_mass_gross_sikn', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (31, 9, 'daily_mass_gross_increasing_sikn', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (32, 10, 'daily_vol_sikn', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (33, 11, 'daily_vol_increasing_sikn', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Суточный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (34, 1, 'daily_mass_bik', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (35, 2, 'daily_temp_bik', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (36, 3, 'daily_pressure_bik', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (37, 4, 'daily_density_bik', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (38, 5, 'daily_density20_bik', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (39, 6, 'daily_density15_bik', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (40, 7, 'daily_moisture_bik', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (41, 8, 'daily_mass_gross_bik', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (42, 9, 'daily_mass_gross_increasing_bik', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (43, 10, 'daily_vol_bik', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Суточный')),
       (44, 11, 'daily_vol_increasing_bik', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Суточный'));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (1, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 20.04.2022',
        TO_TIMESTAMP('2022-04-20 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 23, 1),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 24, 1),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 25, 1),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 26, 1),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 27, 1),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 28, 1),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 29, 1),
       (225, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 30, 1),
       (80353, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 31, 1),
       (279, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 32, 1),
       (86360, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 33, 1);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 5, 1),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 6, 1),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 8, 1),
       (1787, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 9, 1),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 10, 1),
       (1978, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 11, 1);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 12, 1),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 13, 1),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 14, 1),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 15, 1),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 16, 1),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 17, 1),
       (225, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 19, 1),
       (78566, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 20, 1),
       (279, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 21, 1),
       (84382, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 22, 1);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 35, 1),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 36, 1),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 37, 1),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 38, 1),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 39, 1),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 40, 1);

-- Для часового отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (45, 1, 'hour_mass_il1', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (46, 2, 'hour_temp_il1', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (47, 3, 'hour_pressure_il1', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (48, 4, 'hour_density_il1', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (49, 5, 'hour_density20_il1', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (50, 6, 'hour_density15_il1', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (51, 7, 'hour_moisture_il1', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (52, 8, 'hour_mass_gross_il1', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (53, 9, 'hour_mass_gross_increasing_il1', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (54, 10, 'hour_vol_il1', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (55, 11, 'hour_vol_increasing_il1', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Часовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (56, 1, 'hour_mass_il2', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (57, 2, 'hour_temp_il2', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (58, 3, 'hour_pressure_il2', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (59, 4, 'hour_density_il2', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (60, 5, 'hour_density20_il2', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (61, 6, 'hour_density15_il2', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (62, 7, 'hour_moisture_il2', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (63, 8, 'hour_mass_gross_il2', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (64, 9, 'hour_mass_gross_increasing_il2', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (65, 10, 'hour_vol_il2', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (66, 11, 'hour_vol_increasing_il2', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (67, 12, 'hour_vol_decreasing_il2', 'Объем убывающий', (SELECT id FROM report_type WHERE name = 'Часовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (68, 1, 'hour_mass_sikn', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (69, 2, 'hour_temp_sikn', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (70, 3, 'hour_pressure_sikn', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (71, 4, 'hour_density_sikn', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (72, 5, 'hour_density20_sikn', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (73, 6, 'hour_density15_sikn', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (74, 7, 'hour_moisture_sikn', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (75, 8, 'hour_mass_gross_sikn', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (76, 9, 'hour_mass_gross_increasing_sikn', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (77, 10, 'hour_vol_sikn', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (78, 11, 'hour_vol_increasing_sikn', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Часовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (79, 1, 'hour_mass_bik', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (80, 2, 'hour_temp_bik', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (81, 3, 'hour_pressure_bik', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (82, 4, 'hour_density_bik', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (83, 5, 'hour_density20_bik', 'Средняя плотность при 20 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (84, 6, 'hour_density15_bik', 'Средняя плотность при 15 °C', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (85, 7, 'hour_moisture_bik', 'Среднее влагосодержание', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (86, 8, 'hour_mass_gross_bik', 'Масса брутто за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (87, 9, 'hour_mass_gross_increasing_bik', 'Масса брутто нарастающая', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (88, 10, 'hour_vol_bik', 'Объем за сутки', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (89, 11, 'hour_vol_increasing_bik', 'Объем нарастающий', (SELECT id FROM report_type WHERE name = 'Часовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (90, 1, 'hour_mass_il4', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (91, 2, 'hour_temp_il4', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (92, 3, 'hour_pressure_il4', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Часовой'));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (2, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 12 часов 20.04.2022',
        TO_TIMESTAMP('2022-04-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 13:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 13:01:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 68, 2),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 69, 2),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 70, 2),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 71, 2),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 72, 2),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 73, 2),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 74, 2),
       (225, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 75, 2),
       (80353, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 76, 2),
       (279, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 77, 2),
       (86360, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 78, 2);

-- ИЛ1
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 45, 2),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 50, 2),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 51, 2),
       (1787, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 52, 2),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 53, 2),
       (1978, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 55, 2);

-- ИЛ2
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 60, 2),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 61, 2),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 62, 2),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 63, 2),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 64, 2),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 65, 2),
       (225, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 66, 2),
       (78566, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 67, 2),
       (279, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 59, 2),
       (84382, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 58, 2);

-- БИК
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 80, 2),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 81, 2),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 82, 2),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 83, 2),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 84, 2),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 85, 2);

-- ИЛ4
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 90, 2),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 91, 2),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 92, 2);

--Для сменного отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (93, 1, 'shift_mass_il1', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (94, 2, 'shift_temp_il1', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (95, 3, 'shift_pressure_il1', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (96, 4, 'shift_density_il1', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Сменный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (97, 1, 'shift_mass_il2', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (98, 2, 'shift_temp_il2', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (99, 3, 'shift_pressure_il2', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (100, 4, 'shift_density_il2', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Сменный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (101, 1, 'shift_mass_sikn', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (102, 2, 'shift_temp_sikn', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (103, 3, 'shift_pressure_sikn', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Сменный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (104, 1, 'shift_mass_bik', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (105, 2, 'shift_temp_bik', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (106, 3, 'shift_pressure_bik', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Сменный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (107, 1, 'shift_mass_il4', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (108, 2, 'shift_temp_il4', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Сменный')),
       (109, 3, 'shift_pressure_il4', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Сменный'));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (3, (SELECT id FROM report_type WHERE name = 'Сменный'),
        'Сменный отчет за 1 смену 20.04.2022',
        TO_TIMESTAMP('2022-04-20 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 22:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-20 22:00:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 101, 3),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 102, 3),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 103, 3),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 71, 3);

-- ИЛ1
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 93, 3),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 94, 3),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 95, 3),
       (1787, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 96, 3);

-- ИЛ2
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 97, 3),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 98, 3),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 99, 3),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 3);

-- БИК
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 104, 3),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 105, 3),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 106, 3);

-- ИЛ4
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 107, 3),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 108, 3),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 109, 3);

--Для месячного отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (110, 1, 'month_mass_il1', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (111, 2, 'month_temp_il1', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (112, 3, 'month_pressure_il1', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (113, 4, 'month_density_il1', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Месячный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (114, 1, 'month_mass_il2', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (115, 2, 'month_temp_il2', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (116, 3, 'month_pressure_il2', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (117, 4, 'month_density_il2', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Месячный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (118, 1, 'month_mass_sikn', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (119, 2, 'month_temp_sikn', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (120, 3, 'month_pressure_sikn', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Месячный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (121, 1, 'month_mass_bik', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (122, 2, 'month_temp_bik', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (123, 3, 'month_pressure_bik', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Месячный'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (124, 1, 'month_mass_il4', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (125, 2, 'month_temp_il4', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Месячный')),
       (126, 3, 'month_pressure_il4', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Месячный'));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (4, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за 04.2022',
        TO_TIMESTAMP('2022-04-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-05-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-05-01 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 118, 4),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 119, 4),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 120, 4);

-- ИЛ1
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 110, 4),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 111, 4),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 112, 4),
       (1787, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 113, 4);

-- ИЛ2
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 114, 4),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 115, 4),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 116, 4),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 117, 4);

-- БИК
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 121, 4),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 122, 4),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 123, 4);

-- ИЛ4
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 124, 4),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 125, 4),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 126, 4);

--Для годового отчета
INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (127, 1, 'year_mass_sikn', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (128, 2, 'year_temp_sikn', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (129, 3, 'year_pressure_sikn', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Годовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (130, 1, 'year_mass_il1', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (131, 2, 'year_temp_il1', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (132, 3, 'year_pressure_il1', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (133, 4, 'year_density_il1', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Годовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (134, 1, 'year_mass_il2', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (135, 2, 'year_temp_il2', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (136, 3, 'year_pressure_il2', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (137, 4, 'year_density_il2', 'Средняя плотность при текущих t и P', (SELECT id FROM report_type WHERE name = 'Годовой'));


INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (138, 1, 'year_mass_bik', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (139, 2, 'year_temp_bik', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (140, 3, 'year_pressure_bik', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Годовой'));

INSERT INTO tag_name (id, "order", name, description, report_type_id)
VALUES (141, 1, 'year_mass_il4', 'Средний массовый расход', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (142, 2, 'year_temp_il4', 'Средняя температура', (SELECT id FROM report_type WHERE name = 'Годовой')),
       (143, 3, 'year_pressure_il4', 'Среднее давление', (SELECT id FROM report_type WHERE name = 'Годовой'));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (5, (SELECT id FROM report_type WHERE name = 'Годовой'),
        'Годовой отчет за 2021',
        TO_TIMESTAMP('2021-01-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-01-01 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-01-01 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

-- СИКН
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 127, 5),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 128, 5),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 129, 5);

-- ИЛ1
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 130, 5),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 131, 5),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 132, 5),
       (1787, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 133, 5);

-- ИЛ2
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 134, 5),
       (36.4, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 135, 5),
       (5.39, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 136, 5),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 137, 5);

-- БИК
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 138, 5),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 139, 5),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 140, 5);

-- ИЛ4
INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 141, 5),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 142, 5),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 143, 5);