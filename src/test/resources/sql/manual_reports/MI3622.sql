SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('mi3622', 'МИ3622', 'Поверка расходомеров массовых установкой поверочной на базе расходомеров массовых', TRUE);


-- InitialData
INSERT INTO tag (id, permanent_name, address, description, initial, report_type_id)
VALUES (1, 'Q_ij', 'mi3622.' || 'Q_ij', '', TRUE, 'mi3622');

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('N_e_ij', 'mi3622.' || 'N_e_ij', '', TRUE, 'mi3622'),
       ('N_p_ij', 'mi3622.' || 'N_p_ij', '', TRUE, 'mi3622'),
       ('T_ij', 'mi3622.' || 'T_ij', '', TRUE, 'mi3622'),
       ('M_ij', 'mi3622.' || 'M_ij', '', TRUE, 'mi3622'),
       ('f_p_max', 'mi3622.' || 'f_p_max', '', TRUE, 'mi3622'),
       ('Q_p_max', 'mi3622.' || 'Q_p_max', '', TRUE, 'mi3622'),
       ('MF_p', 'mi3622.' || 'MF_p', '', TRUE, 'mi3622'),
       ('K_e_arr', 'mi3622.' || 'K_e_arr', '', TRUE, 'mi3622'),
       ('ZS', 'mi3622.' || 'ZS', '', TRUE, 'mi3622'),
       ('theta_e', 'mi3622.' || 'theta_e', '', TRUE, 'mi3622'),
       ('theta_t', 'mi3622.' || 'theta_t', '', TRUE, 'mi3622'),
       ('theta_p', 'mi3622.' || 'theta_p', '', TRUE, 'mi3622'),
       ('theta_N', 'mi3622.' || 'theta_N', '', TRUE, 'mi3622'),
       ('theta_PDt', 'mi3622.' || 'theta_PDt', '', TRUE, 'mi3622'),
       ('theta_PDp', 'mi3622.' || 'theta_PDp', '', TRUE, 'mi3622'),
       ('theta_Dt', 'mi3622.' || 'theta_Dt', '', TRUE, 'mi3622'),
       ('theta_Dp', 'mi3622.' || 'theta_Dp', '', TRUE, 'mi3622'),
       ('MFOrK', 'mi3622.' || 'MFOrK', '', TRUE, 'mi3622'),
       ('zeroStabilityCorr', 'mi3622.' || 'zeroStabilityCorr', '', TRUE, 'mi3622'),
       ('rangeType', 'mi3622.' || 'rangeType', '', TRUE, 'mi3622'),
       ('operatingOrControlCPM', 'mi3622.' || 'operatingOrControlCPM', '', TRUE, 'mi3622'),
       ('Q_e_arr', 'mi3622.' || 'Q_e_arr', '', TRUE, 'mi3622');

-- FinalData
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('K_pm', 'mi3622.' || 'K_pm', '', FALSE, 'mi3622'),
       ('M_e_ij', 'mi3622.' || 'M_e_ij', '', FALSE, 'mi3622'),
       ('MF_ij', 'mi3622.' || 'MF_ij', '', FALSE, 'mi3622'),
       ('MF', 'mi3622.' || 'MF', '', FALSE, 'mi3622'),
       ('MF_j', 'mi3622.' || 'MF_j', '', FALSE, 'mi3622'),
       ('K', 'mi3622.' || 'K', '', FALSE, 'mi3622'),
       ('K_j', 'mi3622.' || 'K_j', '', FALSE, 'mi3622'),
       ('K_ij', 'mi3622.' || 'K_ij', '', FALSE, 'mi3622'),
       ('MF_prime', 'mi3622.' || 'MF_prime', '', FALSE, 'mi3622'),
       ('f_ij', 'mi3622.' || 'f_ij', '', FALSE, 'mi3622'),
       ('f_j', 'mi3622.' || 'f_j', '', FALSE, 'mi3622'),
       ('S_j', 'mi3622.' || 'S_j', '', FALSE, 'mi3622'),
       ('S_0j', 'mi3622.' || 'S_0j', '', FALSE, 'mi3622'),
       ('eps_j', 'mi3622.' || 'eps_j', '', FALSE, 'mi3622'),
       ('t_095', 'mi3622.' || 't_095', '', FALSE, 'mi3622'),
       ('eps_D', 'mi3622.' || 'eps_D', '', FALSE, 'mi3622'),
       ('Q_j', 'mi3622.' || 'Q_j', '', FALSE, 'mi3622'),
       ('theta_sigma_j', 'mi3622.' || 'theta_sigma_j', '', FALSE, 'mi3622'),
       ('eps_PDk', 'mi3622.' || 'eps_PDk', '', FALSE, 'mi3622'),
       ('theta_sigma_D', 'mi3622.' || 'theta_sigma_D', '', FALSE, 'mi3622'),
       ('theta_sigma_PDk', 'mi3622.' || 'theta_sigma_PDk', '', FALSE, 'mi3622'),
       ('delta_j', 'mi3622.' || 'delta_j', '', FALSE, 'mi3622'),
       ('t_sigma_j', 'mi3622.' || 't_sigma_j', '', FALSE, 'mi3622'),
       ('S_sigma_j', 'mi3622.' || 'S_sigma_j', '', FALSE, 'mi3622'),
       ('S_theta_j', 'mi3622.' || 'S_theta_j', '', FALSE, 'mi3622'),
       ('delta_D', 'mi3622.' || 'delta_D', '', FALSE, 'mi3622'),
       ('delta_PDk', 'mi3622.' || 'delta_PDk', '', FALSE, 'mi3622'),
       ('t_sigma_PDk', 'mi3622.' || 't_sigma_PDk', '', FALSE, 'mi3622'),
       ('S_sigma_PDk', 'mi3622.' || 'S_sigma_PDk', '', FALSE, 'mi3622'),
       ('S_theta_PDk', 'mi3622.' || 'S_theta_PDk', '', FALSE, 'mi3622'),
       ('S_PDk', 'mi3622.' || 'S_PDk', '', FALSE, 'mi3622'),
       ('theta_zj', 'mi3622.' || 'theta_zj', '', FALSE, 'mi3622'),
       ('theta_Dz', 'mi3622.' || 'theta_Dz', '', FALSE, 'mi3622'),
       ('Q_min', 'mi3622.' || 'Q_min', '', FALSE, 'mi3622'),
       ('Q_max', 'mi3622.' || 'Q_max', '', FALSE, 'mi3622'),
       ('Q_min_k', 'mi3622.' || 'Q_min_k', '', FALSE, 'mi3622'),
       ('Q_max_k', 'mi3622.' || 'Q_max_k', '', FALSE, 'mi3622'),
       ('theta_D', 'mi3622.' || 'theta_D', '', FALSE, 'mi3622'),
       ('theta_PDz', 'mi3622.' || 'theta_PDz', '', FALSE, 'mi3622'),
       ('theta_PDk', 'mi3622.' || 'theta_PDk', '', FALSE, 'mi3622'),
       ('conclusion', 'mi3622.' || 'conclusion', '', FALSE, 'mi3622');

-- InitialUnusedData
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('delta_t_dop', 'mi3622.' || 'delta_t_dop', '', TRUE, 'mi3622'),
       ('delta_P_dop', 'mi3622.' || 'delta_P_dop', '', TRUE, 'mi3622'),
       ('t_min', 'mi3622.' || 't_min', '', TRUE, 'mi3622'),
       ('t_max', 'mi3622.' || 't_max', '', TRUE, 'mi3622'),
       ('P_min', 'mi3622.' || 'P_min', '', TRUE, 'mi3622'),
       ('P_max', 'mi3622.' || 'P_max', '', TRUE, 'mi3622'),
       ('t_ih', 'mi3622.' || 't_ih', '', TRUE, 'mi3622'),
       ('P_ij', 'mi3622.' || 'P_ij', '', TRUE, 'mi3622'),
       ('Q_p', 'mi3622.' || 'Q_p', '', TRUE, 'mi3622'),
       ('f_p', 'mi3622.' || 'f_p', '', TRUE, 'mi3622'),
       ( 'K_y', 'mi3622.' || 'K_y', '', TRUE, 'mi3622'),
       ('protocolNumber', 'mi3622.' || 'protocolNumber', '', TRUE, 'mi3622');

-- InitialTextData
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('check_inspection', 'mi3622.' || 'check_inspection', '', TRUE, 'mi3622'),
       ('check_leakproofness', 'mi3622.' || 'check_leakproofness', '', TRUE, 'mi3622'),
       ('check_software', 'mi3622.' || 'check_software', '', TRUE, 'mi3622'),
       ('CPM_name', 'mi3622.' || 'CPM_name', '', TRUE, 'mi3622'),
       ('inspector_full_name', 'mi3622.' || 'inspector_full_name', '', TRUE, 'mi3622'),
       ('poverka_place', 'mi3622.' || 'poverka_place', '', TRUE, 'mi3622'),
       ('inspector_position', 'mi3622.' || 'inspector_position', '', TRUE, 'mi3622'),
       ('check_testing', 'mi3622.' || 'check_testing', '', TRUE, 'mi3622'),
       ('CPM_number', 'mi3622.' || 'CPM_number', '', TRUE, 'mi3622'),
       ('PR_name', 'mi3622.' || 'PR_name', '', TRUE, 'mi3622'),
       ('poverka_method', 'mi3622.' || 'poverka_method', '', TRUE, 'mi3622'),
       ('CPM_owner', 'mi3622.' || 'CPM_owner', '', TRUE, 'mi3622'),
       ('PR_number', 'mi3622.' || 'PR_number', '', TRUE, 'mi3622'),
       ('measureCount', 'mi3622.' || 'measureCount', '', TRUE, 'mi3622'),
       ('pointsCount', 'mi3622.' || 'pointsCount', '', TRUE, 'mi3622'),
       ('S_theta_D', 'mi3622.' || 'S_theta_D', '', FALSE, 'mi3622'),
       ('S_sigma_D', 'mi3622.' || 'S_sigma_D', '', FALSE, 'mi3622'),
       ('t_sigma_D', 'mi3622.' || 't_sigma_D', '', FALSE, 'mi3622'),
       ('S_D', 'mi3622.' || 'S_D', '', FALSE, 'mi3622');



SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(id, name, creation_dt, report_type_id)
VALUES (1, 'Поверка 3622 за 2022-07-14',
        TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'mi3622');

INSERT INTO tag (permanent_name, address, initial, report_type_id)
VALUES ('isFinished', 'mi3622.' || 'isFinished', FALSE, 'mi3622');

SELECT SETVAL('report_id_seq', (SELECT MAX(id) FROM report));