drop table report_name;
drop table tag_data;
drop table report_type;
drop table tag_name;

create table tag_name(
id bigserial primary key,
name varchar(256),
description varchar(256)
);

create table report_type(
id serial primary key,
name varchar(256),
description varchar(256),
active int
);

create table report_name(
id bigserial primary key,
report_type_id int references report_type(id),
report_name varchar(256),
date_creation timestamp
);

create table tag_data(
id bigserial primary key,
data numeric,
date_creation timestamp,
name_id bigint references tag_name(id),
report_id bigint references report_name(id)
);

--insert block
insert into tag_name (name, description)
values
('hour_mass_il1', 'масса за час ил1'),
('hour_vol_il1', 'объем за час ил1'),
('hour_mass_il2', 'масса за час ил2'),
('hour_vol_il2', 'объем за час ил2’'),
('hour_sikn_mass', 'масса за час по сикн'),
('hour_sikn_vol', 'объем за час по сикн');

insert into report_type (name, description, active)
values
('Часовой', 'Отчеты фомрируемые каждый час', 1),
('Суточный', 'Отчеты фомрируемые за сутки', 1),
('Сменный', 'Отчеты фомрируемые за смену', 1),
('Месячный', 'Отчеты фомрируемые за месяц', 1),
('Годовой', 'Отчеты формируемые за год', 1);


insert into report_name(report_type_id, report_name, date_creation)
values
(1, 'Часовой отчет за 12 часов', to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
(1, 'Часовой отчет за 13 часов', to_timestamp('2022-05-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
(1, 'Часовой отчет за 14 часов', to_timestamp('2022-05-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

insert into report_name(report_type_id, report_name, date_creation)
values
(2, 'Суточный отчет за 20 мая', to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
(2, 'Суточный отчет за 21 мая', to_timestamp('2022-05-21 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
(2, 'Суточный отчет за 22 мая', to_timestamp('2022-05-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

insert into report_name(report_type_id, report_name, date_creation)
values
(4, 'Месячный отчет за май', to_timestamp('2022-05-20 12:00:50', 'YYYY-MM-DD HH24:MI:SS')),
(4, 'Месячный отчет за июнь', to_timestamp('2022-06-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS')),
(4, 'Месячный отчет за июль', to_timestamp('2022-07-20 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

insert into tag_data(data, date_creation, name_id, report_id)
values
(80.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 3),
(120.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 3),
(160.0, to_timestamp('2022-05-20 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 3);

select * from tag_data;


