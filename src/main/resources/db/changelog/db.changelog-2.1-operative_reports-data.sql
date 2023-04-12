--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO operative_reports.report_type (id, name, description, active)
VALUES ('hour', 'Часовой', 'Отчеты, формируемые каждый час', TRUE),
       ('twohour', 'Двухчасовой', 'Отчеты, формируемые каждые 2 часа', TRUE),
       ('daily', 'Суточный', 'Отчеты, формируемые за сутки', TRUE),
       ('shift', 'Сменный', 'Отчеты, формируемые за смену', TRUE),
       ('month', 'Месячный', 'Отчеты, формируемые за месяц', TRUE),
       ('year', 'Годовой', 'Отчеты, формируемые за год', TRUE);

insert into operative_reports.report_row (id, name, "order", report_type_id)
values (1, 'Средний массовый расход', 1,'daily'),
       (2, 'Средняя температура', 2,'daily'),
       (3, 'Среднее давление', 3,'daily'),
       (4, 'Средняя плотность при текущих t и P', 4,'daily'),
       (5, 'Средняя плотность при 20 °C', 5,'daily'),
       (6, 'Средняя плотность при 15 °C', 6,'daily'),
       (7, 'Среднее влагосодержание', 7,'daily'),
       (8, 'Масса брутто за сутки', 8,'daily'),
       (9, 'Масса брутто нарастающая', 9,'daily'),
       (10, 'Объем за сутки', 10,'daily'),
       (11, 'Объем нарастающий', 11,'daily');

INSERT INTO operative_reports.metering_node (id, name)
VALUES ('sikn', 'СИКН'),
       ('bik', 'БИК'),
       ('il1', 'ИЛ №1'),
       ('il2', 'ИЛ №2'),
       ('il3', 'ИЛ №3'),
       ('il4', 'ИЛ №4');