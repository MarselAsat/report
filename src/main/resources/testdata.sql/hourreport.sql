SET search_path TO recurring_reports;

INSERT INTO tag_name (id, row_id, name, description, report_type_id)
VALUES (45, 12, 'hour_mass_il1', 'Средний массовый расход за час ил1', 'hour'),
       (46, 13, 'hour_temp_il1', 'Средняя температура за час ил1', 'hour'),
       (47, 14, 'hour_pressure_il1', 'Среднее давление за час ил1', 'hour'),
       (48, 15, 'hour_density_il1', 'Средняя плотность при текущих t и P за час ил1', 'hour'),
       (49, 16, 'hour_density20_il1', 'Средняя плотность при 20 °C за час ил1', 'hour'),
       (50, 17, 'hour_density15_il1', 'Средняя плотность при 15 °C за час ил1', 'hour'),
       (51, 18, 'hour_moisture_il1', 'Среднее влагосодержание за час ил1', 'hour'),
       (52, 19, 'hour_mass_gross_il1', 'Масса брутто за час за час ил1', 'hour'),
       (53, 20, 'hour_mass_gross_increasing_il1', 'Масса брутто нарастающая за час ил1', 'hour'),
       (54, 21, 'hour_vol_il1', 'Объем за час за час ил1', 'hour'),
       (55, 22, 'hour_vol_increasing_il1', 'Объем нарастающий за час ил1', 'hour');

INSERT INTO tag_name (id, row_id, name, description, report_type_id)
VALUES (56, 12, 'hour_mass_il2', 'Средний массовый расход за час ил2', 'hour'),
       (57, 13, 'hour_temp_il2', 'Средняя температура за час ил2', 'hour'),
       (58, 14, 'hour_pressure_il2', 'Среднее давление за час ил2', 'hour'),
       (59, 15, 'hour_density_il2', 'Средняя плотность при текущих t и P за час ил2', 'hour'),
       (60, 16, 'hour_density20_il2', 'Средняя плотность при 20 °C за час ил2', 'hour'),
       (61, 17, 'hour_density15_il2', 'Средняя плотность при 15 °C за час ил2', 'hour'),
       (62, 18, 'hour_moisture_il2', 'Среднее влагосодержание за час ил2', 'hour'),
       (63, 19, 'hour_mass_gross_il2', 'Масса брутто за час ил2', 'hour'),
       (64, 20, 'hour_mass_gross_increasing_il2', 'Масса брутто нарастающая за час ил2', 'hour'),
       (65, 21, 'hour_vol_il2', 'Объем за час ил2', 'hour'),
       (66, 22, 'hour_vol_increasing_il2', 'Объем нарастающий за час ил2', 'hour');

INSERT INTO tag_name (id, row_id, name, description, report_type_id)
VALUES (67, 12, 'hour_mass_sikn', 'Средний массовый расход за час сикн', 'hour'),
       (68, 13, 'hour_temp_sikn', 'Средняя температура за час сикн', 'hour'),
       (69, 14, 'hour_pressure_sikn', 'Среднее давление за час сикн', 'hour'),
       (70, 15, 'hour_density_sikn', 'Средняя плотность при текущих t и P за час сикн', 'hour'),
       (71, 16, 'hour_density20_sikn', 'Средняя плотность при 20 °C за час сикн', 'hour'),
       (72, 17, 'hour_density15_sikn', 'Средняя плотность при 15 °C за час сикн', 'hour'),
       (73, 18, 'hour_moisture_sikn', 'Среднее влагосодержание за час сикн', 'hour'),
       (74, 19, 'hour_mass_gross_sikn', 'Масса брутто за час сикн', 'hour'),
       (75, 20, 'hour_mass_gross_increasing_sikn', 'Масса брутто нарастающая за час сикн', 'hour'),
       (76, 21, 'hour_vol_sikn', 'Объем за час сикн', 'hour'),
       (77, 22, 'hour_vol_increasing_sikn', 'Объем нарастающий за час сикн', 'hour');

INSERT INTO tag_name (id, row_id, name, description, report_type_id)
VALUES (78, 12, 'hour_mass_bik', 'Средний массовый расход', 'hour'),
       (79, 13, 'hour_temp_bik', 'Средняя температура', 'hour'),
       (80, 14, 'hour_pressure_bik', 'Среднее давление', 'hour'),
       (81, 15, 'hour_density_bik', 'Средняя плотность при текущих t и P', 'hour'),
       (82, 16, 'hour_density20_bik', 'Средняя плотность при 20 °C', 'hour'),
       (83, 17, 'hour_density15_bik', 'Средняя плотность при 15 °C', 'hour'),
       (84, 18, 'hour_moisture_bik', 'Среднее влагосодержание', 'hour'),
       (85, 19, 'hour_mass_gross_bik', 'Масса брутто за час', 'hour'),
       (86, 20, 'hour_mass_gross_increasing_bik', 'Масса брутто нарастающая', 'hour'),
       (87, 21, 'hour_vol_bik', 'Объем за час', 'hour'),
       (88, 22, 'hour_vol_increasing_bik', 'Объем нарастающий', 'hour');
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, start_dt, end_dt, creation_dt)
VALUES (2, 'hour',
        'Тестовый часовой отчет за 10:00 21.04.2022',
        TO_TIMESTAMP('2022-04-21 09:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 56, 2),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 57, 2),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 58, 2),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 59, 2),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 60, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 61, 2),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 62, 2),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 63, 2),
       (80353, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 64, 2),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 65, 2),
       (86360, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 66, 2);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 45, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 46, 2),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 47, 2),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 48, 2),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 49, 2),
       (1978, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 50, 2);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 67, 2),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 68, 2),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 69, 2),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 70, 2),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 71, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 72, 2),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 73, 2),
       (78566, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 74, 2),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 75, 2),
       (84382, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 76, 2);

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 78, 2),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 79, 2),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 80, 2),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 81, 2),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 82, 2),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 83, 2);