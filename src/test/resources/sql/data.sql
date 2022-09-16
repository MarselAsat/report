INSERT INTO "user" (username, password, role)
VALUES ('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN'),
       ('user', '$2a$12$C9C9MlJl/AOOp4UVYkujn.Lxld46KI4SOGXWG34tb0jBE52nKXMGa', 'ROLE_USER');

INSERT INTO report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты формируемые за год', TRUE),
       ('manual', 'Ручной', 'Для поверок', TRUE);

INSERT INTO manual_tag_name (id, permanent_name, name, description, initial, type)
VALUES (1, 'Q_ij', 'tag_' || 'Q_ij', '', TRUE, '3622'),
       (2, 'N_e_ij', 'tag_' || 'N_e_ij', '', TRUE, '3622'),
       (3, 'N_p_ij', 'tag_' || 'N_p_ij', '', TRUE, '3622'),
       (4, 'T_ij', 'tag_' || 'T_ij', '', TRUE, '3622'),
       (5, 'M_ij', 'tag_' || 'M_ij', '', TRUE, '3622'),
       (6, 'f_p_max', 'tag_' || 'f_p_max', '', TRUE, '3622'),
       (7, 'Q_p_max', 'tag_' || 'Q_p_max', '', TRUE, '3622'),
       (8, 'MF_p', 'tag_' || 'MF_p', '', TRUE, '3622'),
       (9, 'K_e_ij', 'tag_' || 'K_e_ij', '', TRUE, '3622'),
       (10, 'ZS', 'tag_' || 'ZS', '', TRUE, '3622'),
       (11, 'theta_e', 'tag_' || 'theta_e', '', TRUE, '3622'),
       (12, 'theta_t', 'tag_' || 'theta_t', '', TRUE, '3622'),
       (13, 'theta_p', 'tag_' || 'theta_p', '', TRUE, '3622'),
       (14, 'theta_N', 'tag_' || 'theta_N', '', TRUE, '3622'),
       (15, 'theta_PDt', 'tag_' || 'theta_PDt', '', TRUE, '3622'),
       (16, 'theta_PDp', 'tag_' || 'theta_PDp', '', TRUE, '3622'),
       (17, 'theta_Dt', 'tag_' || 'theta_Dt', '', TRUE, '3622'),
       (18, 'theta_Dp', 'tag_' || 'theta_Dp', '', TRUE, '3622');

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
VALUES (1, 'hour_mass_il1', 'масса за час ил1', 'hour'),
       (2, 'hour_vol_il1', 'объем за час ил1', 'hour'),
       (3, 'hour_mass_il2', 'масса за час ил2', 'hour'),
       (4, 'hour_vol_il2', 'объем за час ил2’', 'hour'),
       (5, 'hour_sikn_mass', 'масса за час по сикн', 'hour'),
       (6, 'hour_sikn_vol', 'объем за час по сикн', 'hour'),
       (7, 'WinCC_OA.report_redu.save', '', 'manual'),
       (8, 'WinCC_OA.report_redu.main', '', 'manual'),
       (9, 'WinCC_OA.CRC.Calc_crc', '', 'manual'),
       (10, 'ns=2;s=Channel1.Device1.Tag3', '', 'manual'),
       (11, 'ns=2;s=Channel1.Device1.Tag5', '', 'manual'),
       (12, 'ns=2;s=Channel1.Device1.Tag4', '', 'manual'),
       (13, 'ns=2;s=Channel1.Device1.Tag6', '', 'manual');
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

INSERT INTO report_name(id, report_type_id, name, creation_dt)
VALUES (27, 'manual',
        'Поверка 3622',
        TO_TIMESTAMP('2022-07-14 12:00:50', 'YYYY-MM-DD HH24:MI:SS'));

SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report_name));

INSERT INTO tag_data(data, creation_dt, tag_name_id, report_name_id)
VALUES (80.0, TO_TIMESTAMP('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 1),
       (120.0, TO_TIMESTAMP('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 1),
       (160.0, TO_TIMESTAMP('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 1),
       (81.0, TO_TIMESTAMP('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 4),
       (121.0, TO_TIMESTAMP('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 4),
       (161.0, TO_TIMESTAMP('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 4),
       (82.0, TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 3),
       (122.0, TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 3),
       (162.0, TO_TIMESTAMP('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 3),
       (83.0, TO_TIMESTAMP('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 2),
       (123.0, TO_TIMESTAMP('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 2),
       (163.0, TO_TIMESTAMP('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 2);

INSERT INTO text_tag_data(data, creation_dt, manual_tag_name_id, report_name_id)
VALUES ('Micro Motion', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_name'), 10),
       ('МИ 3622-2020', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'poverka_method'), 10),
       ('УКУ ДТ, ИЛ-2', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'poverka_place'), 10),
       ('Micro Motion', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'PR_name'), 10),
       ('ЯМАЛ СПГ', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_owner'), 10),
       ('144780098097', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_number'), 10);