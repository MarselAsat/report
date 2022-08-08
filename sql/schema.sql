drop table if exists tag_data;
drop table if exists text_tag_data;
drop table if exists report_name;
drop table if exists tag_name;
drop table if exists manual_tag_name;
drop table if exists "user";
drop table if exists report_type;

create table report_type(
id serial primary key,
name varchar(256),
description varchar(256),
time_zone int,
active boolean
);

create table tag_name(
id bigserial primary key,
name varchar(256),
description varchar(256),
report_type_id int references report_type(id),
unique(name, report_type_id)
);

create table manual_tag_name(
permanent_name varchar(256) primary key,
name varchar(256),
description varchar(256)
);

ALTER TABLE manual_tag_name
ADD COLUMN initial boolean,
ADD COLUMN type varchar(32);

create table report_name(
id bigserial primary key,
report_type_id int references report_type(id),
name varchar(256),
date_creation timestamp
);

create table tag_data(
id bigserial primary key,
data numeric,
date_creation timestamp,
tag_name_id bigint references tag_name(id),
report_name_id bigint references report_name(id)
);

create table text_tag_data(
id bigserial primary key,
data varchar(256),
date_creation timestamp,
tag_name_id bigint references tag_name(id),
report_name_id bigint references report_name(id)
);

create table "user" (
id serial primary key,
username varchar(256),
password varchar(256),
role varchar(256)
);

insert into "user" (username, password, role)
values
('admin', '$2a$12$uNrGCD3abAGLrDySH2TTB.17nbxrHZrWZ6ZeuW52sOWQKUniq9hlG', 'ROLE_ADMIN'),
('user', '$2a$12$C9C9MlJl/AOOp4UVYkujn.Lxld46KI4SOGXWG34tb0jBE52nKXMGa', 'ROLE_USER');

--insert block
insert into report_type (name, description, active)
values
('Часовой', 'Отчеты фомрируемые каждый час', true),
('Суточный', 'Отчеты фомрируемые за сутки', true),
('Сменный', 'Отчеты фомрируемые за смену', true),
('Месячный', 'Отчеты фомрируемые за месяц', true),
('Годовой', 'Отчеты формируемые за год', true),
('Ручной', 'Для поверок', true);

insert into tag_name (name, description, report_type_id)
values
('hour_mass_il1', 'масса за час ил1', 1),
('hour_vol_il1', 'объем за час ил1', 1),
('hour_mass_il2', 'масса за час ил2', 1),
('hour_vol_il2', 'объем за час ил2’', 1),
('hour_sikn_mass', 'масса за час по сикн', 1),
('hour_sikn_vol', 'объем за час по сикн', 1),
('WinCC_OA.report_redu.save', '', 1),
('WinCC_OA.report_redu.main', '', 1),
('WinCC_OA.CRC.Calc_crc', '', 1),
('ns=2;s=Channel1.Device1.Tag3', '', 1),
('ns=2;s=Channel1.Device1.Tag5', '', 1),
('ns=2;s=Channel1.Device1.Tag4', '', 1),
('ns=2;s=Channel1.Device1.Tag6', '', 1),
('factory_number', 'Заводской №', 6),
('owner', 'Владелец', 6),
('PR1', 'ПР1 (тип, модель)', 6),
('place_poverka', 'Место проведения поверки', 6),
('poverka_method', 'Методика поверки', 6),
('CPM', 'СРМ (тип, модель, изготовитель)', 6);

insert into manual_tag_name (permanent_name, name, initial, type)
values
('Q_ij', 'pov_Q', true, '3622'),
('N_ij', 'pov_N', true, '3622'),
('N_e_ij', 'pov_N_e', true, '3622'),
('M_ij', 'pov_M', true, '3622'),
('N_p_ij', 'pov_N_p', true, '3622'),
('f_p_max', 'pov_f_p_max', true, '3622'),
('Q_p_max', 'pov_Q_p', true, '3622'),
('MF_p', 'pov_MF_p', true, '3622'),
('T_ij', 'pov_T_ij', true, '3622'),
('K_e_ij', 'pov_K_e', true, '3622');

insert into report_name(report_type_id, name, date_creation)
values
(1, 'Часовой отчет за 12 часов', to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
(1, 'Часовой отчет за 13 часов', to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
(1, 'Часовой отчет за 14 часов', to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

insert into report_name(report_type_id, name, date_creation)
values
(2, 'Суточный отчет за 20 мая', to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
(2, 'Суточный отчет за 21 мая', to_timestamp('2022-05-21 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
(2, 'Суточный отчет за 22 мая', to_timestamp('2022-05-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

insert into report_name(report_type_id, name, date_creation)
values
(4, 'Месячный отчет за май', to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
(4, 'Месячный отчет за июнь', to_timestamp('2022-06-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
(4, 'Месячный отчет за июль', to_timestamp('2022-07-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

insert into report_name(report_type_id, name, date_creation)
values
(6, 'Поверка 3622', to_timestamp('2022-07-14 12:00:50', 'YYYY-MM-DD HH24:MI:SS'));

insert into tag_data(data, date_creation, tag_name_id, report_name_id)
values
(80.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 1),
(120.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 1),
(160.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 1),
(80.0, to_timestamp('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 4),
(120.0, to_timestamp('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 4),
(160.0, to_timestamp('2022-05-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 4),
(80.0, to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 3),
(120.0, to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 3),
(160.0, to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 3),
(80.0, to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 2),
(120.0, to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 2),
(160.0, to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 2);

insert into text_tag_data(data, date_creation, tag_name_id, report_name_id)
values
('Micro Motion', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 15, 10),
('МИ 3622-2020', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 14, 10),
('УКУ ДТ, ИЛ-2', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 13, 10),
('Micro Motion', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 12, 10),
('ЯМАЛ СПГ', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 11, 10),
('144780098097', to_timestamp('2022-07-14 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10, 10);

select * from tag_data;


