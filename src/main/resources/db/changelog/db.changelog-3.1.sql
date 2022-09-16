--liquibase formatted sql

--changeset alina.parfenteva:1

UPDATE settings
SET name = 'hour report columns'
WHERE name = 'Часовой отчет: столбцы';

UPDATE settings
SET name = 'daily report columns'
WHERE name = 'Суточный отчет: столбцы';

UPDATE settings
SET name = 'shift report columns'
WHERE name = 'Сменный отчет: столбцы';

UPDATE settings
SET name = 'month report columns'
WHERE name = 'Месячный отчет: столбцы';

UPDATE settings
SET name = 'year report columns'
WHERE name = 'Годовой отчет: столбцы';