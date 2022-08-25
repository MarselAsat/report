--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO "user" (username, password, role)
VALUES ('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN'),
       ('user', '$2a$12$C9C9MlJl/AOOp4UVYkujn.Lxld46KI4SOGXWG34tb0jBE52nKXMGa', 'ROLE_USER');

--changeset alina.parfenteva:2
INSERT INTO report_type (name, description, active)
VALUES ('Часовой', 'Отчеты фомируемые каждый час', TRUE),
       ('Суточный', 'Отчеты фомируемые за сутки', TRUE),
       ('Сменный', 'Отчеты фомируемые за смену', TRUE),
       ('Месячный', 'Отчеты фомируемые за месяц', TRUE),
       ('Годовой', 'Отчеты формируемые за год', TRUE),
       ('Ручной', 'Для поверок', TRUE);

--changeset alina.parfenteva:3
INSERT INTO manual_tag_name (permanent_name, name, description, initial, type)
VALUES ('Q_ij', 'tag_'||'Q_ij', '', true, '3622'),
       ('N_e_ij', 'tag_'||'N_e_ij', '', true, '3622'),
       ('N_p_ij', 'tag_'||'N_p_ij', '', true, '3622'),
       ('T_ij', 'tag_'||'T_ij', '', true, '3622'),
       ('M_ij', 'tag_'||'M_ij', '', true, '3622'),
       ('f_p_max', 'tag_'||'f_p_max', '', true, '3622'),
       ('Q_p_max', 'tag_'||'Q_p_max', '', true, '3622'),
       ('MF_p', 'tag_'||'MF_p', '', true, '3622'),
       ('K_e_ij', 'tag_'||'K_e_ij', '', true, '3622'),
       ('ZS', 'tag_'||'ZS', '', true, '3622'),
       ('theta_e', 'tag_'||'theta_e', '', true, '3622'),
       ('theta_t', 'tag_'||'theta_t', '', true, '3622'),
       ('theta_p', 'tag_'||'theta_p', '', true, '3622'),
       ('theta_N', 'tag_'||'theta_N', '', true, '3622'),
       ('theta_PDt', 'tag_'||'theta_PDt', '', true, '3622'),
       ('theta_PDp', 'tag_'||'theta_PDp', '', true, '3622'),
       ('theta_Dt', 'tag_'||'theta_Dt', '', true, '3622'),
       ('theta_Dp', 'tag_'||'theta_Dp', '', true, '3622');