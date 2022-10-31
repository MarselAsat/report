SET search_path TO poverka;

-- InitialData
INSERT INTO tag_name (id, permanent_name, name, description, initial, type)
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

-- InitialTextData
INSERT INTO tag_name (id, permanent_name, name, initial, type)
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

-- FinalData
INSERT INTO tag_name (id, permanent_name, name, description, initial, type)
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

-- InitialUnusedData
INSERT INTO tag_name (id, permanent_name, name, description, initial, type)
VALUES (75, 'delta_t_dop', 'WinCC_OA.rep_test.' || 'delta_t_dop', '', TRUE, 'MI_3622'),
       (76, 'delta_P_dop', 'WinCC_OA.rep_test.' || 'delta_P_dop', '', TRUE, 'MI_3622'),
       (77, 't_min', 'WinCC_OA.rep_test.' || 't_min', '', TRUE, 'MI_3622'),
       (78, 't_max', 'WinCC_OA.rep_test.' || 't_max', '', TRUE, 'MI_3622'),
       (79, 'P_min', 'WinCC_OA.rep_test.' || 'P_min', '', TRUE, 'MI_3622'),
       (80, 'P_max', 'WinCC_OA.rep_test.' || 'P_max', '', TRUE, 'MI_3622'),
       (81, 't_ih', 'WinCC_OA.rep_test.' || 't_ih', '', TRUE, 'MI_3622'),
       (82, 'P_ij', 'WinCC_OA.rep_test.' || 'P_ij', '', TRUE, 'MI_3622'),
       (83, 'Q_p', 'WinCC_OA.rep_test.' || 'Q_p', '', TRUE, 'MI_3622'),
       (84, 'f_p', 'WinCC_OA.rep_test.' || 'f_p', '', TRUE, 'MI_3622'),
       (85, 'K_y', 'WinCC_OA.rep_test.' || 'K_y', '', TRUE, 'MI_3622');

SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, name, creation_dt, poverka_type)
VALUES (1, 'Поверка 3622 за 2022-07-14',
        TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'MI_3622');

SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report_name));