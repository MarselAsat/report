--liquibase formatted sql

--changeset andrey.elchev:1
--liquibase formatted sql

INSERT INTO scheduled_reports.report_type (id, name, description, active)
VALUES
       ('minute', 'минутный', 'Отчеты, формируемые за минут', TRUE);


