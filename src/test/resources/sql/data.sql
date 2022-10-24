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
VALUES (1, 'Q_ij', 'WinCC_OA.rep_test.' || 'Q_ij', '', TRUE, 'MI_3622'),
       (2, 'N_e_ij', 'WinCC_OA.rep_test.' || 'N_e_ij', '', TRUE, 'MI_3622'),
       (3, 'N_p_ij', 'WinCC_OA.rep_test.' || 'N_p_ij', '', TRUE, 'MI_3622'),
       (4, 'T_ij', 'WinCC_OA.rep_test.' || 'T_ij', '', TRUE, 'MI_3622'),
       (5, 'M_ij', 'WinCC_OA.rep_test.' || 'M_ij', '', TRUE, 'MI_3622'),
       (6, 'f_p_max', 'WinCC_OA.rep_test.' || 'f_p_max', '', TRUE, 'MI_3622'),
       (7, 'Q_p_max', 'WinCC_OA.rep_test.' || 'Q_p_max', '', TRUE, 'MI_3622'),
       (8, 'MF_p', 'WinCC_OA.rep_test.' || 'MF_p', '', TRUE, 'MI_3622'),
       (9, 'K_e_ij', 'WinCC_OA.rep_test.' || 'K_e_ij', '', TRUE, 'MI_3622'),
       (10, 'ZS', 'WinCC_OA.rep_test.' || 'ZS', '', TRUE, 'MI_3622'),
       (11, 'theta_e', 'WinCC_OA.rep_test.' || 'theta_e', '', TRUE, 'MI_3622'),
       (12, 'theta_t', 'WinCC_OA.rep_test.' || 'theta_t', '', TRUE, 'MI_3622'),
       (13, 'theta_p', 'WinCC_OA.rep_test.' || 'theta_p', '', TRUE, 'MI_3622'),
       (14, 'theta_N', 'WinCC_OA.rep_test.' || 'theta_N', '', TRUE, 'MI_3622'),
       (15, 'theta_PDt', 'WinCC_OA.rep_test.' || 'theta_PDt', '', TRUE, 'MI_3622'),
       (16, 'theta_PDp', 'WinCC_OA.rep_test.' || 'theta_PDp', '', TRUE, 'MI_3622'),
       (17, 'theta_Dt', 'WinCC_OA.rep_test.' || 'theta_Dt', '', TRUE, 'MI_3622'),
       (18, 'theta_Dp', 'WinCC_OA.rep_test.' || 'theta_Dp', '', TRUE, 'MI_3622');

INSERT INTO manual_tag_name (id, permanent_name, name, initial, type)
VALUES (19, 'CPM_name', 'CPM_name', TRUE, 'MI_3622'),
       (20, 'CPM_number', 'CPM_number', TRUE, 'MI_3622'),
       (21, 'CPM_owner', 'CPM_owner', TRUE, 'MI_3622'),
       (22, 'poverka_method', 'poverka_method', TRUE, 'MI_3622'),
       (23, 'poverka_place', 'poverka_place', TRUE, 'MI_3622'),
       (24, 'PR_name', 'PR_name', TRUE, 'MI_3622'),
       (25, 'PR_number', 'PR_number', TRUE, 'MI_3622'),
       (26, 'check_leakproofness', 'check_leakproofness', TRUE, 'MI_3622'),
       (27, 'check_inspection', 'check_inspection', TRUE, 'MI_3622'),
       (28, 'check_software', 'check_software', TRUE, 'MI_3622'),
       (29, 'check_testing', 'check_testing', TRUE, 'MI_3622'),
       (30, 'inspector_position', 'inspector_position', TRUE, 'MI_3622'),
       (31, 'inspector_full_name', 'inspector_full_name', TRUE, 'MI_3622'),
       (32, 'date', 'date', TRUE, 'MI_3622'),
       (33, 'measureCount', 'WinCC_OA.rep_test.' || 'measureCount', TRUE, 'MI_3622'),
       (34, 'pointsCount', 'WinCC_OA.rep_test.' || 'pointsCount', TRUE, 'MI_3622');

INSERT INTO manual_tag_name (id, permanent_name, name, description, initial, type)
VALUES (35, 'K_pm', 'WinCC_OA.rep_test.' || 'K_pm', '', FALSE, 'MI_3622'),
       (36, 'M_e_ij', 'WinCC_OA.rep_test.' || 'M_e_ij', '', FALSE, 'MI_3622'),
       (37, 'MF_ij', 'WinCC_OA.rep_test.' || 'MF_ij', '', FALSE, 'MI_3622'),
       (38, 'MF', 'WinCC_OA.rep_test.' || 'MF', '', FALSE, 'MI_3622'),
       (39, 'MF_j', 'WinCC_OA.rep_test.' || 'MF_j', '', FALSE, 'MI_3622'),
       (40, 'K', 'WinCC_OA.rep_test.' || 'K', '', FALSE, 'MI_3622'),
       (41, 'K_j', 'WinCC_OA.rep_test.' || 'K_j', '', FALSE, 'MI_3622'),
       (42, 'K_ij', 'WinCC_OA.rep_test.' || 'K_ij', '', FALSE, 'MI_3622'),
       (43, 'MF_prime', 'WinCC_OA.rep_test.' || 'MF_prime', '', FALSE, 'MI_3622'),
       (44, 'f_ij', 'WinCC_OA.rep_test.' || 'f_ij', '', FALSE, 'MI_3622'),
       (45, 'f_j', 'WinCC_OA.rep_test.' || 'f_j', '', FALSE, 'MI_3622'),
       (46, 'S_j', 'WinCC_OA.rep_test.' || 'S_j', '', FALSE, 'MI_3622'),
       (47, 'S_0j', 'WinCC_OA.rep_test.' || 'S_0j', '', FALSE, 'MI_3622'),
       (48, 'eps_j', 'WinCC_OA.rep_test.' || 'eps_j', '', FALSE, 'MI_3622'),
       (49, 't_095', 'WinCC_OA.rep_test.' || 't_095', '', FALSE, 'MI_3622'),
       (50, 'eps_D', 'WinCC_OA.rep_test.' || 'eps_D', '', FALSE, 'MI_3622'),
       (51, 'Q_j', 'WinCC_OA.rep_test.' || 'Q_j', '', FALSE, 'MI_3622'),
       (52, 'theta_sigma_j', 'WinCC_OA.rep_test.' || 'theta_sigma_j', '', FALSE, 'MI_3622'),
       (53, 'eps_PDk', 'WinCC_OA.rep_test.' || 'eps_PDk', '', FALSE, 'MI_3622'),
       (54, 'theta_sigma_D', 'WinCC_OA.rep_test.' || 'theta_sigma_D', '', FALSE, 'MI_3622'),
       (55, 'theta_sigma_PDk', 'WinCC_OA.rep_test.' || 'theta_sigma_PDk', '', FALSE, 'MI_3622'),
       (56, 'delta_j', 'WinCC_OA.rep_test.' || 'delta_j', '', FALSE, 'MI_3622'),
       (57, 't_sigma_j', 'WinCC_OA.rep_test.' || 't_sigma_j', '', FALSE, 'MI_3622'),
       (58, 'S_sigma_j', 'WinCC_OA.rep_test.' || 'S_sigma_j', '', FALSE, 'MI_3622'),
       (59, 'S_theta_j', 'WinCC_OA.rep_test.' || 'S_theta_j', '', FALSE, 'MI_3622'),
       (60, 'delta_D', 'WinCC_OA.rep_test.' || 'delta_D', '', FALSE, 'MI_3622'),
       (61, 'delta_PDk', 'WinCC_OA.rep_test.' || 'delta_PDk', '', FALSE, 'MI_3622'),
       (62, 't_sigma_PDk', 'WinCC_OA.rep_test.' || 't_sigma_PDk', '', FALSE, 'MI_3622'),
       (63, 'S_sigma_PDk', 'WinCC_OA.rep_test.' || 'S_sigma_PDk', '', FALSE, 'MI_3622'),
       (64, 'S_theta_PDk', 'WinCC_OA.rep_test.' || 'S_theta_PDk', '', FALSE, 'MI_3622'),
       (65, 'S_PDk', 'WinCC_OA.rep_test.' || 'S_PDk', '', FALSE, 'MI_3622'),
       (66, 'theta_zj', 'WinCC_OA.rep_test.' || 'theta_zj', '', FALSE, 'MI_3622'),
       (67, 'theta_Dz', 'WinCC_OA.rep_test.' || 'theta_Dz', '', FALSE, 'MI_3622'),
       (68, 'Q_min', 'WinCC_OA.rep_test.' || 'Q_min', '', FALSE, 'MI_3622'),
       (69, 'Q_max', 'WinCC_OA.rep_test.' || 'Q_max', '', FALSE, 'MI_3622'),
       (70, 'Q_min_k', 'WinCC_OA.rep_test.' || 'Q_min_k', '', FALSE, 'MI_3622'),
       (71, 'Q_max_k', 'WinCC_OA.rep_test.' || 'Q_max_k', '', FALSE, 'MI_3622'),
       (72, 'theta_D', 'WinCC_OA.rep_test.' || 'theta_D', '', FALSE, 'MI_3622'),
       (73, 'theta_PDz', 'WinCC_OA.rep_test.' || 'theta_PDz', '', FALSE, 'MI_3622'),
       (74, 'theta_PDk', 'WinCC_OA.rep_test.' || 'theta_PDk', '', FALSE, 'MI_3622');

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
        'Поверка MI_3622',
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
       ('МИ MI_3622-2020', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'poverka_method'), 10),
       ('УКУ ДТ, ИЛ-2', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'poverka_place'), 10),
       ('Micro Motion', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'PR_name'), 10),
       ('ЯМАЛ СПГ', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_owner'), 10),
       ('144780098097', TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT id FROM manual_tag_name WHERE name = 'CRM_number'), 10);