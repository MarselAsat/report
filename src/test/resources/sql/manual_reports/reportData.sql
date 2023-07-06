SET search_path TO manual_reports;

-- InitialData
INSERT INTO tag (id, permanent_name, address, description, initial, report_type)
VALUES (1, 'Q_ij', 'WinCC_OA.rep_test.' || 'Q_ij', '', TRUE, 'MI_3622'),
       (2, 'N_e_ij', 'WinCC_OA.rep_test.' || 'N_e_ij', '', TRUE, 'MI_3622'),
       (3, 'N_p_ij', 'WinCC_OA.rep_test.' || 'N_p_ij', '', TRUE, 'MI_3622'),
       (4, 'T_ij', 'WinCC_OA.rep_test.' || 'T_ij', '', TRUE, 'MI_3622');

SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

INSERT INTO report(id, name, creation_dt, report_type)
VALUES (1, 'Поверка 3622 за 2022-07-14',
        TO_TIMESTAMP('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'MI_3622');

SELECT SETVAL('report_id_seq', (SELECT MAX(id) FROM report));

INSERT INTO report_data(id, "data", tag_id, report_id)
VALUES (1, '[1, 2, 3, 4, 5]', 1, 1),
       (2, '[1, 2, 3, 4, 5]', 2, 1),
       (3, '[1, 2, 3, 4, 5]', 3, 1),
       (4, '[1, 2, 3, 4, 5]', 4, 1);

SELECT SETVAL('manual_report_data_id_seq', (SELECT MAX(id) FROM report_data));