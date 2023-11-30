--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO scheduled_reports.report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты, формируемые каждый час', TRUE),
       ('twohour', 'Двухчасовой', 'Отчеты, формируемые каждые 2 часа', TRUE),
       ('daily', 'Суточный', 'Отчеты, формируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты, формируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты, формируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты, формируемые за год', TRUE);

INSERT INTO scheduled_reports.metering_node (id, name)
VALUES ('sikn', 'СИКН'),
       ('bik', 'БИК'),
       ('il1', 'ИЛ №1'),
       ('il2', 'ИЛ №2'),
       ('il3', 'ИЛ №3'),
       ('il4', 'ИЛ №4');