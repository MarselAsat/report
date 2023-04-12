SET search_path TO calculations;

-- InitialData
INSERT INTO tag_name (id, permanent_name, name, description, initial, calc_method)
VALUES (1, 'Q_ij', 'WinCC_OA.rep_test.' || 'Q_ij', '', TRUE, 'MI_3622');

SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO tag_name (permanent_name, name, description, initial, calc_method)
VALUES ('N_e_ij', 'WinCC_OA.rep_test.' || 'N_e_ij', '', TRUE, 'MI_3622'),
       ('N_p_ij', 'WinCC_OA.rep_test.' || 'N_p_ij', '', TRUE, 'MI_3622'),
       ('T_ij', 'WinCC_OA.rep_test.' || 'T_ij', '', TRUE, 'MI_3622'),
       ('M_ij', 'WinCC_OA.rep_test.' || 'M_ij', '', TRUE, 'MI_3622'),
       ('f_p_max', 'WinCC_OA.rep_test.' || 'f_p_max', '', TRUE, 'MI_3622'),
       ('Q_p_max', 'WinCC_OA.rep_test.' || 'Q_p_max', '', TRUE, 'MI_3622'),
       ('MF_p', 'WinCC_OA.rep_test.' || 'MF_p', '', TRUE, 'MI_3622'),
       ('K_e_arr', 'WinCC_OA.rep_test.' || 'K_e_arr', '', TRUE, 'MI_3622'),
       ('ZS', 'WinCC_OA.rep_test.' || 'ZS', '', TRUE, 'MI_3622'),
       ('theta_e', 'WinCC_OA.rep_test.' || 'theta_e', '', TRUE, 'MI_3622'),
       ('theta_t', 'WinCC_OA.rep_test.' || 'theta_t', '', TRUE, 'MI_3622'),
       ('theta_p', 'WinCC_OA.rep_test.' || 'theta_p', '', TRUE, 'MI_3622'),
       ('theta_N', 'WinCC_OA.rep_test.' || 'theta_N', '', TRUE, 'MI_3622'),
       ('theta_PDt', 'WinCC_OA.rep_test.' || 'theta_PDt', '', TRUE, 'MI_3622'),
       ('theta_PDp', 'WinCC_OA.rep_test.' || 'theta_PDp', '', TRUE, 'MI_3622'),
       ('theta_Dt', 'WinCC_OA.rep_test.' || 'theta_Dt', '', TRUE, 'MI_3622'),
       ('theta_Dp', 'WinCC_OA.rep_test.' || 'theta_Dp', '', TRUE, 'MI_3622'),
       ('MFOrK', 'WinCC_OA.rep_test.' || 'MFOrK', '', TRUE, 'MI_3622'),
       ('zeroStabilityCorr', 'WinCC_OA.rep_test.' || 'zeroStabilityCorr', '', TRUE, 'MI_3622'),
       ('rangeType', 'WinCC_OA.rep_test.' || 'rangeType', '', TRUE, 'MI_3622'),
       ('operatingOrControlCPM', 'WinCC_OA.rep_test.' || 'operatingOrControlCPM', '', TRUE, 'MI_3622'),
       ('Q_e_arr', 'WinCC_OA.rep_test.' || 'Q_e_arr', '', TRUE, 'MI_3622');

-- FinalData
INSERT INTO tag_name (permanent_name, name, description, initial, calc_method)
VALUES ('K_pm', 'WinCC_OA.rep_test.' || 'K_pm', '', FALSE, 'MI_3622'),
       ('M_e_ij', 'WinCC_OA.rep_test.' || 'M_e_ij', '', FALSE, 'MI_3622'),
       ('MF_ij', 'WinCC_OA.rep_test.' || 'MF_ij', '', FALSE, 'MI_3622'),
       ('MF', 'WinCC_OA.rep_test.' || 'MF', '', FALSE, 'MI_3622'),
       ('MF_j', 'WinCC_OA.rep_test.' || 'MF_j', '', FALSE, 'MI_3622'),
       ('K', 'WinCC_OA.rep_test.' || 'K', '', FALSE, 'MI_3622'),
       ('K_j', 'WinCC_OA.rep_test.' || 'K_j', '', FALSE, 'MI_3622'),
       ('K_ij', 'WinCC_OA.rep_test.' || 'K_ij', '', FALSE, 'MI_3622'),
       ('MF_prime', 'WinCC_OA.rep_test.' || 'MF_prime', '', FALSE, 'MI_3622'),
       ('f_ij', 'WinCC_OA.rep_test.' || 'f_ij', '', FALSE, 'MI_3622'),
       ('f_j', 'WinCC_OA.rep_test.' || 'f_j', '', FALSE, 'MI_3622'),
       ('S_j', 'WinCC_OA.rep_test.' || 'S_j', '', FALSE, 'MI_3622'),
       ('S_0j', 'WinCC_OA.rep_test.' || 'S_0j', '', FALSE, 'MI_3622'),
       ('eps_j', 'WinCC_OA.rep_test.' || 'eps_j', '', FALSE, 'MI_3622'),
       ('t_095', 'WinCC_OA.rep_test.' || 't_095', '', FALSE, 'MI_3622'),
       ('eps_D', 'WinCC_OA.rep_test.' || 'eps_D', '', FALSE, 'MI_3622'),
       ('Q_j', 'WinCC_OA.rep_test.' || 'Q_j', '', FALSE, 'MI_3622'),
       ('theta_sigma_j', 'WinCC_OA.rep_test.' || 'theta_sigma_j', '', FALSE, 'MI_3622'),
       ('eps_PDk', 'WinCC_OA.rep_test.' || 'eps_PDk', '', FALSE, 'MI_3622'),
       ('theta_sigma_D', 'WinCC_OA.rep_test.' || 'theta_sigma_D', '', FALSE, 'MI_3622'),
       ('theta_sigma_PDk', 'WinCC_OA.rep_test.' || 'theta_sigma_PDk', '', FALSE, 'MI_3622'),
       ('delta_j', 'WinCC_OA.rep_test.' || 'delta_j', '', FALSE, 'MI_3622'),
       ('t_sigma_j', 'WinCC_OA.rep_test.' || 't_sigma_j', '', FALSE, 'MI_3622'),
       ('S_sigma_j', 'WinCC_OA.rep_test.' || 'S_sigma_j', '', FALSE, 'MI_3622'),
       ('S_theta_j', 'WinCC_OA.rep_test.' || 'S_theta_j', '', FALSE, 'MI_3622'),
       ('delta_D', 'WinCC_OA.rep_test.' || 'delta_D', '', FALSE, 'MI_3622'),
       ('delta_PDk', 'WinCC_OA.rep_test.' || 'delta_PDk', '', FALSE, 'MI_3622'),
       ('t_sigma_PDk', 'WinCC_OA.rep_test.' || 't_sigma_PDk', '', FALSE, 'MI_3622'),
       ('S_sigma_PDk', 'WinCC_OA.rep_test.' || 'S_sigma_PDk', '', FALSE, 'MI_3622'),
       ('S_theta_PDk', 'WinCC_OA.rep_test.' || 'S_theta_PDk', '', FALSE, 'MI_3622'),
       ('S_PDk', 'WinCC_OA.rep_test.' || 'S_PDk', '', FALSE, 'MI_3622'),
       ('theta_zj', 'WinCC_OA.rep_test.' || 'theta_zj', '', FALSE, 'MI_3622'),
       ('theta_Dz', 'WinCC_OA.rep_test.' || 'theta_Dz', '', FALSE, 'MI_3622'),
       ('Q_min', 'WinCC_OA.rep_test.' || 'Q_min', '', FALSE, 'MI_3622'),
       ('Q_max', 'WinCC_OA.rep_test.' || 'Q_max', '', FALSE, 'MI_3622'),
       ('Q_min_k', 'WinCC_OA.rep_test.' || 'Q_min_k', '', FALSE, 'MI_3622'),
       ('Q_max_k', 'WinCC_OA.rep_test.' || 'Q_max_k', '', FALSE, 'MI_3622'),
       ('theta_D', 'WinCC_OA.rep_test.' || 'theta_D', '', FALSE, 'MI_3622'),
       ('theta_PDz', 'WinCC_OA.rep_test.' || 'theta_PDz', '', FALSE, 'MI_3622'),
       ('theta_PDk', 'WinCC_OA.rep_test.' || 'theta_PDk', '', FALSE, 'MI_3622'),
       ('conclusion', 'WinCC_OA.rep_test.' || 'conclusion', '', FALSE, 'MI_3622');

-- InitialUnusedData
INSERT INTO tag_name (permanent_name, name, description, initial, calc_method)
VALUES ('delta_t_dop', 'WinCC_OA.rep_test.' || 'delta_t_dop', '', TRUE, 'MI_3622'),
       ('delta_P_dop', 'WinCC_OA.rep_test.' || 'delta_P_dop', '', TRUE, 'MI_3622'),
       ('t_min', 'WinCC_OA.rep_test.' || 't_min', '', TRUE, 'MI_3622'),
       ('t_max', 'WinCC_OA.rep_test.' || 't_max', '', TRUE, 'MI_3622'),
       ('P_min', 'WinCC_OA.rep_test.' || 'P_min', '', TRUE, 'MI_3622'),
       ('P_max', 'WinCC_OA.rep_test.' || 'P_max', '', TRUE, 'MI_3622'),
       ('t_ih', 'WinCC_OA.rep_test.' || 't_ih', '', TRUE, 'MI_3622'),
       ('P_ij', 'WinCC_OA.rep_test.' || 'P_ij', '', TRUE, 'MI_3622'),
       ('Q_p', 'WinCC_OA.rep_test.' || 'Q_p', '', TRUE, 'MI_3622'),
       ('f_p', 'WinCC_OA.rep_test.' || 'f_p', '', TRUE, 'MI_3622'),
       ( 'K_y', 'WinCC_OA.rep_test.' || 'K_y', '', TRUE, 'MI_3622'),
       ('protocolNumber', 'WinCC_OA.rep_test.' || 'protocolNumber', '', TRUE, 'MI_3622');

-- InitialTextData
INSERT INTO tag_name (permanent_name, name, description, initial, calc_method)
VALUES ('check_inspection', 'WinCC_OA.rep_test.' || 'check_inspection', '', TRUE, 'MI_3622'),
       ('check_leakproofness', 'WinCC_OA.rep_test.' || 'check_leakproofness', '', TRUE, 'MI_3622'),
       ('check_software', 'WinCC_OA.rep_test.' || 'check_software', '', TRUE, 'MI_3622'),
       ('CPM_name', 'WinCC_OA.rep_test.' || 'CPM_name', '', TRUE, 'MI_3622'),
       ('inspector_full_name', 'WinCC_OA.rep_test.' || 'inspector_full_name', '', TRUE, 'MI_3622'),
       ('calc_place', 'WinCC_OA.rep_test.' || 'calc_place', '', TRUE, 'MI_3622'),
       ('inspector_position', 'WinCC_OA.rep_test.' || 'inspector_position', '', TRUE, 'MI_3622'),
       ('check_testing', 'WinCC_OA.rep_test.' || 'check_testing', '', TRUE, 'MI_3622'),
       ('CPM_number', 'WinCC_OA.rep_test.' || 'CPM_number', '', TRUE, 'MI_3622'),
       ('PR_name', 'WinCC_OA.rep_test.' || 'PR_name', '', TRUE, 'MI_3622'),
       ('calc_method', 'WinCC_OA.rep_test.' || 'calc_method', '', TRUE, 'MI_3622'),
       ('CPM_owner', 'WinCC_OA.rep_test.' || 'CPM_owner', '', TRUE, 'MI_3622'),
       ('PR_number', 'WinCC_OA.rep_test.' || 'PR_number', '', TRUE, 'MI_3622'),
       ('measureCount', 'WinCC_OA.rep_test.' || 'measureCount', '', TRUE, 'MI_3622'),
       ('pointsCount', 'WinCC_OA.rep_test.' || 'pointsCount', '', TRUE, 'MI_3622'),
       ('S_theta_D', 'WinCC_OA.rep_test.' || 'S_theta_D', '', FALSE, 'MI_3622'),
       ('S_sigma_D', 'WinCC_OA.rep_test.' || 'S_sigma_D', '', FALSE, 'MI_3622'),
       ('t_sigma_D', 'WinCC_OA.rep_test.' || 't_sigma_D', '', FALSE, 'MI_3622'),
       ('S_D', 'WinCC_OA.rep_test.' || 'S_D', '', FALSE, 'MI_3622');

SELECT SETVAL('tag_name_id_seq', (SELECT MAX(id) FROM tag_name));

INSERT INTO report_name(id, name, creation_dt, calc_method)
VALUES (1, 'Поверка 3622 за 2022-07-14',
        TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'MI_3622');

SELECT SETVAL('report_name_id_seq', (SELECT MAX(id) FROM report_name));