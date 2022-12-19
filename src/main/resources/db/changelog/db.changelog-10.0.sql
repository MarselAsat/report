--liquibase formatted sql

--changeset alina.parfenteva:1
DELETE
FROM recurring_reports.report_type
WHERE id = 'manual'


