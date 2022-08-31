INSERT INTO tag_name (id, name, description, report_type_id)
VALUES (1, 'hour_mass_il1', 'масса за час ил1', 1),
       (2, 'hour_vol_il1', 'объем за час ил1', 1),
       (3, 'hour_mass_il2', 'масса за час ил2', 1),
       (4, 'hour_vol_il2', 'объем за час ил2’', 1),
       (5, 'hour_sikn_mass', 'масса за час по сикн', 1),
       (6, 'hour_sikn_vol', 'объем за час по сикн', 1),
       (7, 'WinCC_OA.report_redu.save', '', 1),
       (8, 'WinCC_OA.report_redu.main', '', 1),
       (9, 'WinCC_OA.CRC.Calc_crc', '', 1),
       (10, 'ns=2;s=Channel1.Device1.Tag3', '', 1),
       (11, 'ns=2;s=Channel1.Device1.Tag5', '', 1),
       (12, 'ns=2;s=Channel1.Device1.Tag4', '', 1),
       (13, 'ns=2;s=Channel1.Device1.Tag6', '', 1);
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (1, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 12 часов',
        TO_TIMESTAMP('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (2, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 13 часов',
        TO_TIMESTAMP('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (3, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 14 часов',
        TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (4, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 20 мая',
        to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (5, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 21 мая',
        to_timestamp('2022-05-21 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (6, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 22 мая',
        to_timestamp('2022-05-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (7, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за май',
        to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (8, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за июнь',
        to_timestamp('2022-06-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (9, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за июль',
        to_timestamp('2022-07-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (10, (SELECT id FROM report_type WHERE name = 'Ручной'),
        'Поверка 3622',
        to_timestamp('2022-07-14 12:00:50', 'YYYY-MM-DD HH24:MI:SS'));

SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report_name));

INSERT INTO tag_data(data, date_creation, tag_name_id, report_name_id)
VALUES (80.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 1),
       (120.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 1),
       (160.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 1),
       (81.0, to_timestamp('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 4),
       (121.0, to_timestamp('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 4),
       (161.0, to_timestamp('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 4),
       (82.0, to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 3),
       (122.0, to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 3),
       (162.0, to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 3),
       (83.0, to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 2),
       (123.0, to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 2),
       (163.0, to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 2);

INSERT INTO text_tag_data(data, date_creation, manual_tag_name_id, report_name_id)
VALUES ('Micro Motion', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_name'), 10),
       ('МИ 3622-2020', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'poverka_method'), 10),
       ('УКУ ДТ, ИЛ-2', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'poverka_place'), 10),
       ('Micro Motion', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'PR_name'), 10),
       ('ЯМАЛ СПГ', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_owner'), 10),
       ('144780098097', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_number'), 10);