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

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (1, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 12 часов 20.04.2022',
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO tag_data(data, date_creation, tag_name_id, report_name_id)
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

INSERT INTO tag_data(data, date_creation, tag_name_id, report_name_id)
VALUES (815.0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 5, 1),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 6, 1),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 8, 1),
       (1787, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 9, 1),
       (0, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 10, 1),
       (1978, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 11, 1);

INSERT INTO tag_data(data, date_creation, tag_name_id, report_name_id)
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

INSERT INTO tag_data(data, date_creation, tag_name_id, report_name_id)
VALUES (35.7, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 35, 1),
       (5.33, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 36, 1),
       (807.1, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 37, 1),
       (815, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 38, 1),
       (818.8, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 39, 1),
       (0.01, to_timestamp('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 40, 1);
