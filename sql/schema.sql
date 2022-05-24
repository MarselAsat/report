create table tag_name(
id bigserial primary key,
name varchar(256),
descritption varchar(256)
);

create table report_type(
id serial primary key,
name varchar(256),
description varchar(256),
active int
);

create table tag_data(
id bigserial primary key,
data numeric,
date_creation timestamp,
name_id bigint,
report_id int
);

create table report_name(
id bigserial primary key,
report_id int,
report_name varchar(256)
);