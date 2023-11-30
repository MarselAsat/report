SET search_path TO scheduled_reports;

-- Для суточного отчета

insert into scheduled_reports.report_row (id, name, "order", report_type_id)
values (1, 'Средний массовый расход', 1,'daily'),
       (2, 'Средняя температура', 2,'daily'),
       (3, 'Среднее давление', 3,'daily'),
       (4, 'Средняя плотность при текущих t и P', 4,'daily'),
       (5, 'Средняя плотность при 20 °C', 5,'daily'),
       (6, 'Средняя плотность при 15 °C', 6,'daily'),
       (7, 'Среднее влагосодержание', 7,'daily'),
       (8, 'Масса брутто за сутки', 8,'daily'),
       (9, 'Масса брутто нарастающая', 9,'daily'),
       (10, 'Объем за сутки', 10,'daily'),
       (11, 'Объем нарастающий', 11,'daily');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES (1, 'daily_mass_il1', 'Средний массовый расход за сутки ил1', 'daily', 'il1'),
       (2, 'daily_temp_il1', 'Средняя температура за сутки ил1', 'daily', 'il1'),
       (3, 'daily_pressure_il1', 'Среднее давление за сутки ил1', 'daily', 'il1'),
       (4, 'daily_density_il1', 'Средняя плотность при текущих t и P за сутки ил1', 'daily', 'il1'),
       (5, 'daily_density20_il1', 'Средняя плотность при 20 °C за сутки ил1', 'daily', 'il1'),
       (6, 'daily_density15_il1', 'Средняя плотность при 15 °C за сутки ил1', 'daily', 'il1'),
       (7, 'daily_moisture_il1', 'Среднее влагосодержание за сутки ил1', 'daily', 'il1'),
       (8, 'daily_mass_gross_il1', 'Масса брутто за сутки за сутки ил1', 'daily', 'il1'),
       (9, 'daily_mass_gross_increasing_il1', 'Масса брутто нарастающая за сутки ил1', 'daily', 'il1'),
       (10, 'daily_vol_il1', 'Объем за сутки за сутки ил1', 'daily', 'il1'),
       (11, 'daily_vol_increasing_il1', 'Объем нарастающий за сутки ил1', 'daily', 'il1');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES (1, 'daily_mass_il2', 'Средний массовый расход за сутки ил2', 'daily', 'il2'),
       (2, 'daily_temp_il2', 'Средняя температура за сутки ил2', 'daily', 'il2'),
       (3, 'daily_pressure_il2', 'Среднее давление за сутки ил2', 'daily', 'il2'),
       (4, 'daily_density_il2', 'Средняя плотность при текущих t и P за сутки ил2', 'daily', 'il2'),
       (5, 'daily_density20_il2', 'Средняя плотность при 20 °C за сутки ил2', 'daily', 'il2'),
       (6, 'daily_density15_il2', 'Средняя плотность при 15 °C за сутки ил2', 'daily', 'il2'),
       (7, 'daily_moisture_il2', 'Среднее влагосодержание за сутки ил2', 'daily', 'il2'),
       (8, 'daily_mass_gross_il2', 'Масса брутто за сутки ил2', 'daily', 'il2'),
       (9, 'daily_mass_gross_increasing_il2', 'Масса брутто нарастающая за сутки ил2', 'daily', 'il2'),
       (10, 'daily_vol_il2', 'Объем за сутки ил2', 'daily', 'il2'),
       (11, 'daily_vol_increasing_il2', 'Объем нарастающий за сутки ил2', 'daily', 'il2');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES (1, 'daily_mass_sikn', 'Средний массовый расход за сутки сикн', 'daily', 'sikn'),
       (2, 'daily_temp_sikn', 'Средняя температура за сутки сикн', 'daily', 'sikn'),
       (3, 'daily_pressure_sikn', 'Среднее давление за сутки сикн', 'daily', 'sikn'),
       (4, 'daily_density_sikn', 'Средняя плотность при текущих t и P за сутки сикн', 'daily', 'sikn'),
       (5, 'daily_density20_sikn', 'Средняя плотность при 20 °C за сутки сикн', 'daily', 'sikn'),
       (6, 'daily_density15_sikn', 'Средняя плотность при 15 °C за сутки сикн', 'daily', 'sikn'),
       (7, 'daily_moisture_sikn', 'Среднее влагосодержание за сутки сикн', 'daily', 'sikn'),
       (8, 'daily_mass_gross_sikn', 'Масса брутто за сутки сикн', 'daily', 'sikn'),
       (9, 'daily_mass_gross_increasing_sikn', 'Масса брутто нарастающая за сутки сикн', 'daily', 'sikn'),
       (10, 'daily_vol_sikn', 'Объем за сутки сикн', 'daily', 'sikn'),
       (11, 'daily_vol_increasing_sikn', 'Объем нарастающий за сутки сикн', 'daily', 'sikn');

INSERT INTO tag (row_id, address, description, report_type_id, metering_node_id)
VALUES (1, 'daily_mass_bik', 'Средний массовый расход', 'daily', 'bik'),
       (2, 'daily_temp_bik', 'Средняя температура', 'daily', 'bik'),
       (3, 'daily_pressure_bik', 'Среднее давление', 'daily', 'bik'),
       (4, 'daily_density_bik', 'Средняя плотность при текущих t и P', 'daily', 'bik'),
       (5, 'daily_density20_bik', 'Средняя плотность при 20 °C', 'daily', 'bik'),
       (6, 'daily_density15_bik', 'Средняя плотность при 15 °C', 'daily', 'bik'),
       (7, 'daily_moisture_bik', 'Среднее влагосодержание', 'daily', 'bik'),
       (8, 'daily_mass_gross_bik', 'Масса брутто за сутки', 'daily', 'bik'),
       (9, 'daily_mass_gross_increasing_bik', 'Масса брутто нарастающая', 'daily', 'bik'),
       (10, 'daily_vol_bik', 'Объем за сутки', 'daily', 'bik'),
       (11, 'daily_vol_increasing_bik', 'Объем нарастающий', 'daily', 'bik');
SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(report_type_id, name, start_dt, end_dt, creation_dt)
VALUES ('daily',
        'Тестовый суточный отчет за 20.04.2022',
        TO_TIMESTAMP('2022-04-20 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 
        (SELECT id from tag where address='daily_mass_sikn'), 
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_temp_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_pressure_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density20_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density15_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_moisture_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (80353, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_increasing_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_vol_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (86360, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_vol_increasing_sikn'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (815.0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_temp_il1'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_pressure_il1'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density_il1'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (1787, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density20_il1'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (0, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density15_il1'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (1978, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_increasing_il1'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (45.9, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (36.4, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_temp_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (5.39, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_pressure_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density20_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density15_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (225, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_moisture_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (78566, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (279, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_increasing_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (84382, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_vol_il2'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022'));

INSERT INTO report_data(data, creation_dt, tag_id, report_id)
VALUES (35.7, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_bik'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (5.33, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_temp_bik'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (807.1, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density_bik'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (815, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_density15_bik'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (818.8, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_bik'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022')),
       (0.01, TO_TIMESTAMP('2022-04-21 10:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id from tag where address='daily_mass_gross_increasing_bik'),
        (SELECT id from report where name='Тестовый суточный отчет за 20.04.2022'));

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));
SELECT SETVAL('report_id_seq', (SELECT MAX(id) FROM report));