--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO "user" (username, password, role)
VALUES ('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN'),
       ('user', '$2a$12$C9C9MlJl/AOOp4UVYkujn.Lxld46KI4SOGXWG34tb0jBE52nKXMGa', 'ROLE_USER');

--changeset alina.parfenteva:2
-- noinspection SqlResolve
INSERT INTO report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('daily', 'Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты формируемые за год', TRUE),
       ('manual', 'Ручной', 'Для поверок', TRUE);

--changeset alina.parfenteva:3
-- noinspection SqlResolve
INSERT INTO manual_tag_name (permanent_name, name, description, initial, type)
VALUES ('Q_ij', 'WinCC_OA.rep_test.' || 'Q_ij', '', TRUE, '3622'),
       ('N_e_ij', 'WinCC_OA.rep_test.' || 'N_e_ij', '', TRUE, '3622'),
       ('N_p_ij', 'WinCC_OA.rep_test.' || 'N_p_ij', '', TRUE, '3622'),
       ('T_ij', 'WinCC_OA.rep_test.' || 'T_ij', '', TRUE, '3622'),
       ('M_ij', 'WinCC_OA.rep_test.' || 'M_ij', '', TRUE, '3622'),
       ('f_p_max', 'WinCC_OA.rep_test.' || 'f_p_max', '', TRUE, '3622'),
       ('Q_p_max', 'WinCC_OA.rep_test.' || 'Q_p_max', '', TRUE, '3622'),
       ('MF_p', 'WinCC_OA.rep_test.' || 'MF_p', '', TRUE, '3622'),
       ('K_e_arr', 'WinCC_OA.rep_test.' || 'K_e_arr', '', TRUE, '3622'),
       ('Q_e_arr', 'WinCC_OA.rep_test.' || 'Q_e_arr', '', TRUE, '3622'),
       ('ZS', 'WinCC_OA.rep_test.' || 'ZS', '', TRUE, '3622'),
       ('theta_e', 'WinCC_OA.rep_test.' || 'theta_e', '', TRUE, '3622'),
       ('theta_t', 'WinCC_OA.rep_test.' || 'theta_t', '', TRUE, '3622'),
       ('theta_p', 'WinCC_OA.rep_test.' || 'theta_p', '', TRUE, '3622'),
       ('theta_N', 'WinCC_OA.rep_test.' || 'theta_N', '', TRUE, '3622'),
       ('theta_PDt', 'WinCC_OA.rep_test.' || 'theta_PDt', '', TRUE, '3622'),
       ('theta_PDp', 'WinCC_OA.rep_test.' || 'theta_PDp', '', TRUE, '3622'),
       ('theta_Dt', 'WinCC_OA.rep_test.' || 'theta_Dt', '', TRUE, '3622'),
       ('theta_Dp', 'WinCC_OA.rep_test.' || 'theta_Dp', '', TRUE, '3622');