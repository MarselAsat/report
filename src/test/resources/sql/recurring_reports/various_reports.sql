SET search_path TO recurring_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('twohour', 'Двухчасовой', 'Отчеты фомируемые каждые два часа', TRUE),
       ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты формируемые за год', TRUE);

INSERT INTO tag_name (id, name, description, report_type_id)
VALUES (1, 'hour_mass_il1', 'масса за час ил1', 'hour'),
       (2, 'daily_vol_il1', 'объем за сутки ил1', 'daily'),
       (3, 'shift_mass_il2', 'масса за смену ил2', 'shift'),
       (4, 'month_vol_il2', 'объем за месяц ил2’', 'month'),
       (5, 'year_sikn_mass', 'масса за год по сикн', 'year'),
       (6, 'hour_sikn_vol', 'объем за час по сикн', 'hour'),
       (7, 'twohour_sikn_mass', 'масса за 2 часа по сикн', 'twohour');
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, creation_dt)
VALUES (1, 'hour',
        'Часовой отчет за 12 часов 20.05.2022',
        TO_TIMESTAMP('2022-05-20 13:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (2, 'hour',
        'Часовой отчет за 13 часов 20.05.2022',
        TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (3, 'hour',
        'Часовой отчет за 10 часов 1.06.2022',
        TO_TIMESTAMP('2022-06-01 11:10:50', 'YYYY-MM-DD HH24:MI:SS')),
       (4, 'hour',
        'Часовой отчет за 17 часов 01.06.2022',
        TO_TIMESTAMP('2022-06-01 18:01:00', 'YYYY-MM-DD HH24:MI:SS')),
       (5, 'hour',
        'Часовой отчет за 14 часов 20.05.2022',
        TO_TIMESTAMP('2022-05-20 15:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (6, 'hour',
        'Часовой отчет за 17 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 18:01:00', 'YYYY-MM-DD HH24:MI:SS')),
       (7, 'hour',
        'Часовой отчет за 14 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 15:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (8, 'hour',
        'Часовой отчет за 15 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 16:01:00', 'YYYY-MM-DD HH24:MI:SS')),
       (9, 'hour',
        'Часовой отчет за 14 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 15:00:00', 'YYYY-MM-DD HH24:MI:SS'));


INSERT INTO report_name(id, report_type_id, name, creation_dt)
VALUES (10, 'daily',
        'Суточный отчет за 20.05.2022',
        TO_TIMESTAMP('2022-05-21 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (11, 'daily',
        'Суточный отчет за 21.05.2022',
        TO_TIMESTAMP('2022-05-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (12, 'daily',
        'Суточный отчет за 22.05.2022',
        TO_TIMESTAMP('2022-05-23 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (13, 'daily',
        'Суточный отчет за 20.01.2021',
        TO_TIMESTAMP('2021-01-21 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (14, 'daily',
        'Суточный отчет за 21.01.2021',
        TO_TIMESTAMP('2021-01-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (15, 'daily',
        'Суточный отчет за 22.01.2021',
        TO_TIMESTAMP('2021-01-23 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, creation_dt)
VALUES (16, 'month',
        'Месячный отчет за май 2022',
        TO_TIMESTAMP('2022-06-01 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (17, 'month',
        'Месячный отчет за июнь 2022',
        TO_TIMESTAMP('2022-07-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (18, 'month',
        'Месячный отчет за июль 2022',
        TO_TIMESTAMP('2022-08-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (19, 'month',
        'Месячный отчет за январь 2021',
        TO_TIMESTAMP('2021-02-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (20, 'month',
        'Месячный отчет за февраль 2021',
        TO_TIMESTAMP('2021-03-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (21, 'month',
        'Месячный отчет за декабрь 2021',
        TO_TIMESTAMP('2022-01-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, creation_dt)
VALUES (22, 'shift',
        'Сменный отчет за I смену 20.08.2022',
        TO_TIMESTAMP('2022-08-20 22:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (23, 'shift',
        'Сменный отчет за II смену 20.08.2022',
        TO_TIMESTAMP('2022-08-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (24, 'shift',
        'Сменный отчет за I смену 20.08.2021',
        TO_TIMESTAMP('2021-08-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, creation_dt)
VALUES (25, 'year',
        'Годовой отчет за 2022',
        TO_TIMESTAMP('2023-01-01 10:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (26, 'year',
        'Годовой отчет за 2021',
        TO_TIMESTAMP('2022-01-01 22:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, creation_dt, start_dt, end_dt)
VALUES (27, 'twohour',
        'Двухчасовой отчет за период с 10:00 по 12:00 01.03.2023',
        TO_TIMESTAMP('2023-03-01 12:00:50', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2023-03-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2023-03-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (28, 'twohour',
        'Двухчасовой отчет за период с 12:00 по 14:00 01.03.2023',
        TO_TIMESTAMP('2022-03-01 12:01:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2023-03-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2023-03-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report_name));

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (80.0, TO_TIMESTAMP('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 1),
       (120.0, TO_TIMESTAMP('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 6, 1),
       (81.0, TO_TIMESTAMP('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 4),
       (121.0, TO_TIMESTAMP('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 6, 4),
       (82.0, TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 3),
       (122.0, TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 6, 3),
       (83.0, TO_TIMESTAMP('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 2),
       (123.0, TO_TIMESTAMP('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 6, 2);