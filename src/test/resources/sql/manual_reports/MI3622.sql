SET search_path TO manual_reports;

-- InitialData
INSERT INTO tag (id, permanent_name, address, description, initial, report_type)
VALUES (1, 'Q_ij', 'WinCC_OA.mi3622.' || 'Q_ij', '', TRUE, 'MI3622');

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('N_e_ij', 'WinCC_OA.mi3622.' || 'N_e_ij', '', TRUE, 'MI3622'),
       ('N_p_ij', 'WinCC_OA.mi3622.' || 'N_p_ij', '', TRUE, 'MI3622'),
       ('T_ij', 'WinCC_OA.mi3622.' || 'T_ij', '', TRUE, 'MI3622'),
       ('M_ij', 'WinCC_OA.mi3622.' || 'M_ij', '', TRUE, 'MI3622'),
       ('f_p_max', 'WinCC_OA.mi3622.' || 'f_p_max', '', TRUE, 'MI3622'),
       ('Q_p_max', 'WinCC_OA.mi3622.' || 'Q_p_max', '', TRUE, 'MI3622'),
       ('MF_p', 'WinCC_OA.mi3622.' || 'MF_p', '', TRUE, 'MI3622'),
       ('K_e_arr', 'WinCC_OA.mi3622.' || 'K_e_arr', '', TRUE, 'MI3622'),
       ('ZS', 'WinCC_OA.mi3622.' || 'ZS', '', TRUE, 'MI3622'),
       ('theta_e', 'WinCC_OA.mi3622.' || 'theta_e', '', TRUE, 'MI3622'),
       ('theta_t', 'WinCC_OA.mi3622.' || 'theta_t', '', TRUE, 'MI3622'),
       ('theta_p', 'WinCC_OA.mi3622.' || 'theta_p', '', TRUE, 'MI3622'),
       ('theta_N', 'WinCC_OA.mi3622.' || 'theta_N', '', TRUE, 'MI3622'),
       ('theta_PDt', 'WinCC_OA.mi3622.' || 'theta_PDt', '', TRUE, 'MI3622'),
       ('theta_PDp', 'WinCC_OA.mi3622.' || 'theta_PDp', '', TRUE, 'MI3622'),
       ('theta_Dt', 'WinCC_OA.mi3622.' || 'theta_Dt', '', TRUE, 'MI3622'),
       ('theta_Dp', 'WinCC_OA.mi3622.' || 'theta_Dp', '', TRUE, 'MI3622'),
       ('MFOrK', 'WinCC_OA.mi3622.' || 'MFOrK', '', TRUE, 'MI3622'),
       ('zeroStabilityCorr', 'WinCC_OA.mi3622.' || 'zeroStabilityCorr', '', TRUE, 'MI3622'),
       ('rangeType', 'WinCC_OA.mi3622.' || 'rangeType', '', TRUE, 'MI3622'),
       ('operatingOrControlCPM', 'WinCC_OA.mi3622.' || 'operatingOrControlCPM', '', TRUE, 'MI3622'),
       ('Q_e_arr', 'WinCC_OA.mi3622.' || 'Q_e_arr', '', TRUE, 'MI3622');

-- FinalData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('K_pm', 'WinCC_OA.mi3622.' || 'K_pm', '', FALSE, 'MI3622'),
       ('M_e_ij', 'WinCC_OA.mi3622.' || 'M_e_ij', '', FALSE, 'MI3622'),
       ('MF_ij', 'WinCC_OA.mi3622.' || 'MF_ij', '', FALSE, 'MI3622'),
       ('MF', 'WinCC_OA.mi3622.' || 'MF', '', FALSE, 'MI3622'),
       ('MF_j', 'WinCC_OA.mi3622.' || 'MF_j', '', FALSE, 'MI3622'),
       ('K', 'WinCC_OA.mi3622.' || 'K', '', FALSE, 'MI3622'),
       ('K_j', 'WinCC_OA.mi3622.' || 'K_j', '', FALSE, 'MI3622'),
       ('K_ij', 'WinCC_OA.mi3622.' || 'K_ij', '', FALSE, 'MI3622'),
       ('MF_prime', 'WinCC_OA.mi3622.' || 'MF_prime', '', FALSE, 'MI3622'),
       ('f_ij', 'WinCC_OA.mi3622.' || 'f_ij', '', FALSE, 'MI3622'),
       ('f_j', 'WinCC_OA.mi3622.' || 'f_j', '', FALSE, 'MI3622'),
       ('S_j', 'WinCC_OA.mi3622.' || 'S_j', '', FALSE, 'MI3622'),
       ('S_0j', 'WinCC_OA.mi3622.' || 'S_0j', '', FALSE, 'MI3622'),
       ('eps_j', 'WinCC_OA.mi3622.' || 'eps_j', '', FALSE, 'MI3622'),
       ('t_095', 'WinCC_OA.mi3622.' || 't_095', '', FALSE, 'MI3622'),
       ('eps_D', 'WinCC_OA.mi3622.' || 'eps_D', '', FALSE, 'MI3622'),
       ('Q_j', 'WinCC_OA.mi3622.' || 'Q_j', '', FALSE, 'MI3622'),
       ('theta_sigma_j', 'WinCC_OA.mi3622.' || 'theta_sigma_j', '', FALSE, 'MI3622'),
       ('eps_PDk', 'WinCC_OA.mi3622.' || 'eps_PDk', '', FALSE, 'MI3622'),
       ('theta_sigma_D', 'WinCC_OA.mi3622.' || 'theta_sigma_D', '', FALSE, 'MI3622'),
       ('theta_sigma_PDk', 'WinCC_OA.mi3622.' || 'theta_sigma_PDk', '', FALSE, 'MI3622'),
       ('delta_j', 'WinCC_OA.mi3622.' || 'delta_j', '', FALSE, 'MI3622'),
       ('t_sigma_j', 'WinCC_OA.mi3622.' || 't_sigma_j', '', FALSE, 'MI3622'),
       ('S_sigma_j', 'WinCC_OA.mi3622.' || 'S_sigma_j', '', FALSE, 'MI3622'),
       ('S_theta_j', 'WinCC_OA.mi3622.' || 'S_theta_j', '', FALSE, 'MI3622'),
       ('delta_D', 'WinCC_OA.mi3622.' || 'delta_D', '', FALSE, 'MI3622'),
       ('delta_PDk', 'WinCC_OA.mi3622.' || 'delta_PDk', '', FALSE, 'MI3622'),
       ('t_sigma_PDk', 'WinCC_OA.mi3622.' || 't_sigma_PDk', '', FALSE, 'MI3622'),
       ('S_sigma_PDk', 'WinCC_OA.mi3622.' || 'S_sigma_PDk', '', FALSE, 'MI3622'),
       ('S_theta_PDk', 'WinCC_OA.mi3622.' || 'S_theta_PDk', '', FALSE, 'MI3622'),
       ('S_PDk', 'WinCC_OA.mi3622.' || 'S_PDk', '', FALSE, 'MI3622'),
       ('theta_zj', 'WinCC_OA.mi3622.' || 'theta_zj', '', FALSE, 'MI3622'),
       ('theta_Dz', 'WinCC_OA.mi3622.' || 'theta_Dz', '', FALSE, 'MI3622'),
       ('Q_min', 'WinCC_OA.mi3622.' || 'Q_min', '', FALSE, 'MI3622'),
       ('Q_max', 'WinCC_OA.mi3622.' || 'Q_max', '', FALSE, 'MI3622'),
       ('Q_min_k', 'WinCC_OA.mi3622.' || 'Q_min_k', '', FALSE, 'MI3622'),
       ('Q_max_k', 'WinCC_OA.mi3622.' || 'Q_max_k', '', FALSE, 'MI3622'),
       ('theta_D', 'WinCC_OA.mi3622.' || 'theta_D', '', FALSE, 'MI3622'),
       ('theta_PDz', 'WinCC_OA.mi3622.' || 'theta_PDz', '', FALSE, 'MI3622'),
       ('theta_PDk', 'WinCC_OA.mi3622.' || 'theta_PDk', '', FALSE, 'MI3622'),
       ('conclusion', 'WinCC_OA.mi3622.' || 'conclusion', '', FALSE, 'MI3622');

-- InitialUnusedData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('delta_t_dop', 'WinCC_OA.mi3622.' || 'delta_t_dop', '', TRUE, 'MI3622'),
       ('delta_P_dop', 'WinCC_OA.mi3622.' || 'delta_P_dop', '', TRUE, 'MI3622'),
       ('t_min', 'WinCC_OA.mi3622.' || 't_min', '', TRUE, 'MI3622'),
       ('t_max', 'WinCC_OA.mi3622.' || 't_max', '', TRUE, 'MI3622'),
       ('P_min', 'WinCC_OA.mi3622.' || 'P_min', '', TRUE, 'MI3622'),
       ('P_max', 'WinCC_OA.mi3622.' || 'P_max', '', TRUE, 'MI3622'),
       ('t_ih', 'WinCC_OA.mi3622.' || 't_ih', '', TRUE, 'MI3622'),
       ('P_ij', 'WinCC_OA.mi3622.' || 'P_ij', '', TRUE, 'MI3622'),
       ('Q_p', 'WinCC_OA.mi3622.' || 'Q_p', '', TRUE, 'MI3622'),
       ('f_p', 'WinCC_OA.mi3622.' || 'f_p', '', TRUE, 'MI3622'),
       ( 'K_y', 'WinCC_OA.mi3622.' || 'K_y', '', TRUE, 'MI3622'),
       ('protocolNumber', 'WinCC_OA.mi3622.' || 'protocolNumber', '', TRUE, 'MI3622');

-- InitialTextData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('check_inspection', 'WinCC_OA.mi3622.' || 'check_inspection', '', TRUE, 'MI3622'),
       ('check_leakproofness', 'WinCC_OA.mi3622.' || 'check_leakproofness', '', TRUE, 'MI3622'),
       ('check_software', 'WinCC_OA.mi3622.' || 'check_software', '', TRUE, 'MI3622'),
       ('CPM_name', 'WinCC_OA.mi3622.' || 'CPM_name', '', TRUE, 'MI3622'),
       ('inspector_full_name', 'WinCC_OA.mi3622.' || 'inspector_full_name', '', TRUE, 'MI3622'),
       ('poverka_place', 'WinCC_OA.mi3622.' || 'poverka_place', '', TRUE, 'MI3622'),
       ('inspector_position', 'WinCC_OA.mi3622.' || 'inspector_position', '', TRUE, 'MI3622'),
       ('check_testing', 'WinCC_OA.mi3622.' || 'check_testing', '', TRUE, 'MI3622'),
       ('CPM_number', 'WinCC_OA.mi3622.' || 'CPM_number', '', TRUE, 'MI3622'),
       ('PR_name', 'WinCC_OA.mi3622.' || 'PR_name', '', TRUE, 'MI3622'),
       ('poverka_method', 'WinCC_OA.mi3622.' || 'poverka_method', '', TRUE, 'MI3622'),
       ('CPM_owner', 'WinCC_OA.mi3622.' || 'CPM_owner', '', TRUE, 'MI3622'),
       ('PR_number', 'WinCC_OA.mi3622.' || 'PR_number', '', TRUE, 'MI3622'),
       ('measureCount', 'WinCC_OA.mi3622.' || 'measureCount', '', TRUE, 'MI3622'),
       ('pointsCount', 'WinCC_OA.mi3622.' || 'pointsCount', '', TRUE, 'MI3622'),
       ('S_theta_D', 'WinCC_OA.mi3622.' || 'S_theta_D', '', FALSE, 'MI3622'),
       ('S_sigma_D', 'WinCC_OA.mi3622.' || 'S_sigma_D', '', FALSE, 'MI3622'),
       ('t_sigma_D', 'WinCC_OA.mi3622.' || 't_sigma_D', '', FALSE, 'MI3622'),
       ('S_D', 'WinCC_OA.mi3622.' || 'S_D', '', FALSE, 'MI3622');

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(id, name, creation_dt, report_type)
VALUES (1, 'Поверка 3622 за 2022-07-14',
        TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'MI3622');

SELECT SETVAL('report_id_seq', (SELECT MAX(id) FROM report));