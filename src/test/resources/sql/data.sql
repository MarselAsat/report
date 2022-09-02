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

INSERT INTO manual_tag_name (id, permanent_name, name, description, initial, type)
VALUES (1, 'Q_ij', 'tag_'||'Q_ij', '', true, '3622'),
       (2, 'N_e_ij', 'tag_'||'N_e_ij', '', true, '3622'),
       (3, 'N_p_ij', 'tag_'||'N_p_ij', '', true, '3622'),
       (4, 'T_ij', 'tag_'||'T_ij', '', true, '3622'),
       (5, 'M_ij', 'tag_'||'M_ij', '', true, '3622'),
       (6, 'f_p_max', 'tag_'||'f_p_max', '', true, '3622'),
       (7, 'Q_p_max', 'tag_'||'Q_p_max', '', true, '3622'),
       (8, 'MF_p', 'tag_'||'MF_p', '', true, '3622'),
       (9, 'K_e_ij', 'tag_'||'K_e_ij', '', true, '3622'),
       (10, 'ZS', 'tag_'||'ZS', '', true, '3622'),
       (11, 'theta_e', 'tag_'||'theta_e', '', true, '3622'),
       (12, 'theta_t', 'tag_'||'theta_t', '', true, '3622'),
       (13, 'theta_p', 'tag_'||'theta_p', '', true, '3622'),
       (14, 'theta_N', 'tag_'||'theta_N', '', true, '3622'),
       (15, 'theta_PDt', 'tag_'||'theta_PDt', '', true, '3622'),
       (16, 'theta_PDp', 'tag_'||'theta_PDp', '', true, '3622'),
       (17, 'theta_Dt', 'tag_'||'theta_Dt', '', true, '3622'),
       (18, 'theta_Dp', 'tag_'||'theta_Dp', '', true, '3622');

INSERT INTO manual_tag_name (id, permanent_name, name, initial, type)
VALUES (19, 'CPM_name', 'CPM_name', TRUE, '3622'),
       (20, 'CPM_number', 'CPM_number', TRUE, '3622'),
       (21, 'CPM_owner', 'CPM_owner', TRUE, '3622'),
       (22, 'poverka_method', 'poverka_method', TRUE, '3622'),
       (23, 'poverka_place', 'poverka_place', TRUE, '3622'),
       (24, 'PR_name', 'PR_name', TRUE, '3622'),
       (25, 'PR_number', 'PR_number', TRUE, '3622'),
       (26, 'check_leakproofness', 'check_leakproofness', TRUE, '3622'),
       (27, 'check_inspection', 'check_inspection', TRUE, '3622'),
       (28, 'check_software', 'check_software', TRUE, '3622'),
       (29, 'check_testing', 'check_testing', TRUE, '3622'),
       (30, 'inspector_position', 'inspector_position', TRUE, '3622'),
       (31, 'inspector_full_name', 'inspector_full_name', TRUE, '3622'),
       (32, 'date', 'date', TRUE, '3622');
SELECT SETVAL('manual_tag_name_id_seq', (SELECT MAX(id) FROM manual_tag_name));


INSERT INTO tag_name (id, name, description, report_type_id)
VALUES (1, 'hour_mass_il1', 'масса за час ил1', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (2, 'hour_vol_il1', 'объем за час ил1', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (3, 'hour_mass_il2', 'масса за час ил2', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (4, 'hour_vol_il2', 'объем за час ил2’', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (5, 'hour_sikn_mass', 'масса за час по сикн', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (6, 'hour_sikn_vol', 'объем за час по сикн', (SELECT id FROM report_type WHERE name = 'Часовой')),
       (7, 'WinCC_OA.report_redu.save', '', (SELECT id FROM report_type WHERE name = 'Ручной')),
       (8, 'WinCC_OA.report_redu.main', '', (SELECT id FROM report_type WHERE name = 'Ручной')),
       (9, 'WinCC_OA.CRC.Calc_crc', '', (SELECT id FROM report_type WHERE name = 'Ручной')),
       (10, 'ns=2;s=Channel1.Device1.Tag3', '', (SELECT id FROM report_type WHERE name = 'Ручной')),
       (11, 'ns=2;s=Channel1.Device1.Tag5', '', (SELECT id FROM report_type WHERE name = 'Ручной')),
       (12, 'ns=2;s=Channel1.Device1.Tag4', '', (SELECT id FROM report_type WHERE name = 'Ручной')),
       (13, 'ns=2;s=Channel1.Device1.Tag6', '', (SELECT id FROM report_type WHERE name = 'Ручной'));
SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (1, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 12 часов 20.05.2022',
        TO_TIMESTAMP('2022-05-20 13:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (2, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 13 часов 20.05.2022',
        TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (3, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 10 часов 1.06.2022',
        TO_TIMESTAMP('2022-06-01 11:10:50', 'YYYY-MM-DD HH24:MI:SS')),
       (4, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 17 часов 01.06.2022',
        TO_TIMESTAMP('2022-06-01 18:01:00', 'YYYY-MM-DD HH24:MI:SS')),
       (5, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 14 часов 20.05.2022',
        TO_TIMESTAMP('2022-05-20 15:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (6, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 17 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 18:01:00', 'YYYY-MM-DD HH24:MI:SS')),
       (7, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 14 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 15:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (8, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 15 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 16:01:00', 'YYYY-MM-DD HH24:MI:SS')),
       (9, (SELECT id FROM report_type WHERE name = 'Часовой'),
        'Часовой отчет за 14 часов 02.01.2021',
        TO_TIMESTAMP('2021-01-02 15:00:00', 'YYYY-MM-DD HH24:MI:SS'));


INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (10, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 20.05.2022',
        to_timestamp('2022-05-21 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (11, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 21.05.2022',
        to_timestamp('2022-05-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (12, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 22.05.2022',
        to_timestamp('2022-05-23 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (13, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 20.01.2021',
        to_timestamp('2021-01-21 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (14, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 21.01.2021',
        to_timestamp('2021-01-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (15, (SELECT id FROM report_type WHERE name = 'Суточный'),
        'Суточный отчет за 22.01.2021',
        to_timestamp('2021-01-23 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (16, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за май 2022',
        to_timestamp('2022-06-01 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (17, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за июнь 2022',
        to_timestamp('2022-07-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (18, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за июль 2022',
        to_timestamp('2022-08-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (19, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за январь 2021',
        to_timestamp('2021-02-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (20, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за февраль 2021',
        to_timestamp('2021-03-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (21, (SELECT id FROM report_type WHERE name = 'Месячный'),
        'Месячный отчет за декабрь 2021',
        TO_TIMESTAMP('2022-01-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (22, (SELECT id FROM report_type WHERE name = 'Сменный'),
        'Сменный отчет за I смену 20.08.2022',
        to_timestamp('2022-08-20 22:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (23, (SELECT id FROM report_type WHERE name = 'Сменный'),
        'Сменный отчет за II смену 20.08.2022',
        to_timestamp('2022-08-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS')),
       (24, (SELECT id FROM report_type WHERE name = 'Сменный'),
        'Сменный отчет за I смену 20.08.2021',
        to_timestamp('2021-08-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (25, (SELECT id FROM report_type WHERE name = 'Годовой'),
        'Годовой отчет за 2022',
        to_timestamp('2023-01-01 10:00:50', 'YYYY-MM-DD HH24:MI:SS')),
       (26, (SELECT id FROM report_type WHERE name = 'Годовой'),
        'Годовой отчет за 2021',
        to_timestamp('2022-01-01 22:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO report_name(id, report_type_id, name, date_creation)
VALUES (27, (SELECT id FROM report_type WHERE name = 'Ручной'),
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